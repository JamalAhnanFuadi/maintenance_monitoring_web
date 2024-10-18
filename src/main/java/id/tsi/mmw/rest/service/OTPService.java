package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.UserController;
import id.tsi.mmw.filter.ApplicationFilter;
import id.tsi.mmw.model.Principal;
import id.tsi.mmw.model.User;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.EmailValidateRequest;
import id.tsi.mmw.rest.model.request.OTPRequest;
import id.tsi.mmw.rest.validator.OTPValidator;
import id.tsi.mmw.util.helper.EmailHelper;
import id.tsi.mmw.util.helper.FileHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("otp")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OTPService extends BaseService {

    @Inject
    private UserController userController;

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

        boolean validPayload = validator.validateRequestOtp(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        String trackingId = generateTrackingID();

        if (validPayload) {
            // First we need to check if the user exists in the database. If the user does not exist,
            // we will return a 400 Bad Request with a message indicating that the user was not found.
            boolean userExist = userController.validateEmail(request.getEmail());
            log.debug(methodName, "User validation : " + userExist);

            if (userExist) {

                User user = userController.getUserDetailByEmail(request.getEmail());

                // If the user exists,
                Principal principal = new Principal(request.getEmail());
                setSessionAttribute(ApplicationFilter.SESSION_KEY, principal);
                setSessionAttribute(Constants.SESSION_RESET_PASSWORD, true);
                setSessionAttribute(Constants.SESSION_USER, user);
                setSessionAttribute(Constants.SESSION_RESET_PASSWORD_EMAIL, request.getEmail());
                setTrackingID(trackingId);

                // Generate OTP
                String otpCode = generateOTPCode();
                sendResetPasswordEmail(user, otpCode);

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

    private void sendResetPasswordEmail(User user, String otpCode) {

        String subject = "Verify Your Identity: Password Reset OTP";
        String template = FileHelper.readFileFromResources("otp-email-template.txt");
        String body = template
                .replace("{fullName}", user.getFirstname())
                .replace("{otpCode}", otpCode)
                .replace("{expiry}", "5");

        EmailHelper.sendEmail(subject, body, user.getEmail(), null);
    }
}
