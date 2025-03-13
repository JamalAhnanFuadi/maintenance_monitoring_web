package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.OTPController;
import id.tsi.mmw.controller.StaffController;
import id.tsi.mmw.controller.microservice.EmailController;
import id.tsi.mmw.filter.ApplicationFilter;
import id.tsi.mmw.model.OTP;
import id.tsi.mmw.model.Principal;
import id.tsi.mmw.model.Staff;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.property.Property;
import id.tsi.mmw.rest.model.request.OTPRequest;
import id.tsi.mmw.rest.validator.OTPValidator;
import id.tsi.mmw.util.helper.DateHelper;
import id.tsi.mmw.util.helper.FileHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Singleton
@Path("otp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OTPService extends BaseService {

    @Inject
    private StaffController staffController;

    @Inject
    private OTPController otpController;

    @Inject
    private EmailController emailController;

    private OTPValidator validator;

    public OTPService() {
        log = getLogger(this.getClass());
        validator = new OTPValidator();
    }

    @POST
    @Path("/request")
    public Response requestOtp(OTPRequest request) {
        final String methodName = "requestOtp";
        start(methodName);

        Response response;
        log.info(methodName, "Request OTP for (" + request.getEmail() + ")");

        boolean validPayload = validator.validateOtpRequest(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        String trackingId = generateTrackingID();

        if (validPayload) {
            // First we need to check if the user exists in the database. If the user does not exist,
            // we will return a 400 Bad Request with a message indicating that the user was not found.
            boolean userExist = staffController.validateEmail(request.getEmail());
            log.debug(methodName, "User validation : " + userExist);

            if (userExist) {

                Staff staff = staffController.getUserDetailByEmail(request.getEmail());

                // If the user exists,
                Principal principal = new Principal(request.getEmail());
                setSessionAttribute(ApplicationFilter.SESSION_KEY, principal);
                setSessionAttribute(Constants.SESSION_RESET_PASSWORD, true);
                setSessionAttribute(Constants.SESSION_USER, staff);
                setSessionAttribute(Constants.SESSION_RESET_PASSWORD_EMAIL, request.getEmail());
                setTrackingID(trackingId);

                boolean hasOtp= otpController.validateOtp(request.getEmail());
                if (hasOtp) {
                    otpController.deleteOtp(staff.getEmail());
                }

                // Generate OTP
                boolean otpGenerateEnable = getBooleanProperty(Property.OTP_TEST_ENABLE);
                String otpCode="";

                if (otpGenerateEnable) {
                    otpCode = getProperty(Property.OTP_TEST_CODE);
                }else {
                    otpCode = generateOTPCode();
                }
                LocalDateTime ldtNow = LocalDateTime.now();
                LocalDateTime otpExpiry = ldtNow.plusMinutes(getIntegerProperty(Property.OTP_EXPIRY));

                OTP otp = new OTP();
                otp.setUser(staff.getEmail());
                otp.setOtpCode(otpCode);
                otp.setCreateDt(DateHelper.formatDBDateTime(ldtNow));
                otp.setExpiryDt(DateHelper.formatDBDateTime(otpExpiry));
                otp.setRetryCount(0);

                otpController.insertOtp(otp);
                sendResetPasswordEmail(staff, otpCode, getProperty(Property.OTP_EXPIRY));

                response = buildSuccessResponse();
            } else {
                // If the user does not exist, we will return a 400 Bad Request with a message
                // indicating that the user was not found.
                response = buildBadRequestResponse("User not found");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }

        completed(methodName);
        return response;
    }

    private String generateOTPCode() {
        String otpCode = "";

        for (int i = 0; i < 6; i++) {
            otpCode += (int) (Math.random() * 10);
        }
        return otpCode;
    }

    private void sendResetPasswordEmail(Staff staff, String otpCode, String expiry) {

        String subject = "Verify Your Identity: Password Reset OTP";
        String template = FileHelper.readFileFromResources("otp-email-template.txt");
        String body = template
                .replace("{fullName}", staff.getFirstname())
                .replace("{otpCode}", otpCode)
                .replace("{expiry}", expiry);

        emailController.send(staff.getEmail(), subject, body);
        //EmailHelper.sendEmail(subject, body, user.getEmail(), null);
    }

    @POST
    @Path("/validate")
    public Response validateOtp(OTPRequest request) {
        final String methodName = "validateOtp";
        start(methodName);

        Response response;
        log.info(methodName, "Validate OTP for (" + request.getEmail() + ")");

        boolean validPayload = validator.validateOtpValidate(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {

            OTP otp = otpController.validateOtp(request.getOtpCode(), request.getEmail());
            if (otp != null) {
                if(otp.getOtpStatus().equals(Constants.OTP_VALID)) {
                    otpController.deleteOtp(request.getEmail());
                    response = buildSuccessResponse();
                }else {
                    response = buildBadRequestResponse("OTP code expired");
                }
            } else {
                response = buildBadRequestResponse("Invalid OTP code");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }

        completed(methodName);
        return response;
    }
}
