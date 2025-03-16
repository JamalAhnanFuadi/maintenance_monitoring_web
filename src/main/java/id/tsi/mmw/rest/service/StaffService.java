package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.AuthenticationController;
import id.tsi.mmw.controller.StaffController;
import id.tsi.mmw.controller.microservice.EmailController;
import id.tsi.mmw.filter.ApplicationFilter;
import id.tsi.mmw.manager.EncryptionManager;
import id.tsi.mmw.model.Authentication;
import id.tsi.mmw.model.Principal;
import id.tsi.mmw.model.Staff;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.property.Property;
import id.tsi.mmw.rest.model.request.EmailValidateRequest;
import id.tsi.mmw.rest.model.request.UserRequest;
import id.tsi.mmw.rest.validator.UserValidator;
import id.tsi.mmw.util.helper.DateHelper;
import id.tsi.mmw.util.helper.FileHelper;
import id.tsi.mmw.util.json.JsonHelper;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Singleton
@Path("staff")
@Produces(MediaType.APPLICATION_JSON)
public class StaffService extends BaseService {

    @Inject
    private StaffController staffController;

    @Inject
    private AuthenticationController authenticationController;

    @Inject
    private EmailController emailController;

    private UserValidator validator;

    @Inject
    protected ExecutorService executor;

    public StaffService() {
        log = getLogger(this.getClass());
        validator = new UserValidator();
    }

    @POST
    @Path("/validate/email")
    public Response validateEmail(EmailValidateRequest request) {
        final String methodName = "validateEmail";
        start(methodName);

        Response response;
        log.info(methodName, "Validate email (" + request.getEmail() + ")");

        boolean validPayload = validator.validate(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            boolean userExist = staffController.validateEmail(request.getEmail());
            log.debug(methodName, "User validation : " + userExist);

            if (userExist) {

                Staff staff = staffController.getUserDetailByEmail(request.getEmail());

                Principal principal = new Principal(request.getEmail());
                setSessionAttribute(ApplicationFilter.SESSION_KEY, principal);
                setSessionAttribute(Constants.SESSION_USER, staff);

                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("User not found");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }

        completed(methodName);
        return response;
    }

    @GET
    @PermitAll
    public Response getStaffList() {
        final String methodName = "getStaffList";
        start(methodName);
        log.info(methodName, "Get staff List");
        Response response;

        List<Staff> staff = staffController.getStaffList();

        response = buildSuccessResponse(staff);
        completed(methodName);
        return response;
    }

    @GET
    @PermitAll
    @Path("{uid}")
    public Response getStaff(@PathParam("uid") String uid) {
        final String methodName = "getStaff";
        start(methodName);
        Response response;

        log.info(methodName, "Get staff by id (" + uid + ")");

        boolean validStaff = staffController.validateStaff(uid);
        log.debug(methodName, "Staff validation : " + validStaff);

        if (validStaff) {
            Staff staff = staffController.getStaff(uid);
            log.debug(methodName, JsonHelper.toJson(staff));
            response = buildSuccessResponse(staff);
        } else {
            // Build a bad request response if the user id is invalid
            response = buildBadRequestResponse("Invalid User id");
        }
        completed(methodName);
        return response;
    }


    @POST
    @PermitAll
    public Response create(UserRequest request) {
        final String methodName = "create";
        Response response = null;
        start(methodName);
        log.info(methodName, "Create User");
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            boolean emailExist = staffController.validateEmail(request.getEmail());
            log.debug(methodName, "Email exist : " + emailExist);

            if (!emailExist) {
                String uuid = UUID.randomUUID().toString();

                Staff staff = new Staff();
                staff.setUid(uuid);
                staff.setFirstname(request.getFirstname());
                staff.setLastname(request.getLastname());

                staff.setEmail(request.getEmail());
                staff.setMobileNumber(request.getMobileNumber());
                staff.setDepartmentUid(request.getDepartmentUid());

                if(!request.getDob().isEmpty() && request.getDob() != null) {
                    LocalDate dobLD = DateHelper.parseFEDate(request.getDob());
                    LocalDateTime dobLDT = dobLD.atStartOfDay();
                    staff.setDob(DateHelper.formatDBDateTime(dobLDT));
                }

                String processingTime = DateHelper.formatDateTime(LocalDateTime.now());
                staff.setCreateDt(processingTime);

                String salt = EncryptionManager.getInstance().generateRandomString(getIntegerProperty(Property.ENCRYPTION_SALT_LENGTH));
                String defaultPassword = getProperty(Property.STAFF_DEFAULT_PASSWORD);
                String hashedPassword = EncryptionManager.getInstance().hash(defaultPassword, salt);

                Authentication authentication = new Authentication();
                authentication.setUid(uuid);
                authentication.setSalt(salt);
                authentication.setPassword(hashedPassword);

                boolean created = staffController.addStaff(staff);
                log.debug(methodName, "Staff creation : " + created);
                if (created) {
                    boolean createdAuthentication = authenticationController.createAuthentication(authentication);
                    log.debug(methodName, "Authentication creation : " + createdAuthentication);
                    if(createdAuthentication) {
                        executor.execute(() -> sendCreateUserEmail(staff));
                        response = buildSuccessResponse();
                    }else {
                        log.debug(methodName, "Rollback staff creation");
                        staffController.delete(uuid);

                        response = buildBadRequestResponse("Staff creation failed");
                    }
                } else {
                    response = buildBadRequestResponse("Staff creation failed");
                }
            } else {
                response = buildConflictResponse("Staff email already exists");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateStaff(UserRequest request) {
        final String methodName = "updateStaff";
        Response response;
        start(methodName);
        log.info(methodName, "Update staff");
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            boolean validStaff = staffController.validateStaff(request.getUid());
            log.debug(methodName, "Staff validation : " + validStaff);

            if (validStaff) {

                Staff staff = new Staff();
                staff.setUid(request.getUid());
                staff.setFirstname(request.getFirstname());
                staff.setLastname(request.getLastname());
                staff.setEmail(request.getEmail());
                staff.setMobileNumber(request.getMobileNumber());
                staff.setDepartment(request.getDepartmentUid());
                staff.setStatus(request.isStatus());

                if(!request.getDob().isEmpty() && request.getDob() != null) {
                    LocalDate dobLD = DateHelper.parseFEDate(request.getDob());
                    LocalDateTime dobLDT = dobLD.atStartOfDay();
                    staff.setDob(DateHelper.formatDBDateTime(dobLDT));
                }

                String processingTime = DateHelper.formatDateTime(LocalDateTime.now());

                // proceed user update to database
                boolean created = staffController.updateStaff(staff);
                if (created) {

                    // if activate staff, allow user to login
                    // else block staff login
                    if(staff.isStatus()) {
                        authenticationController.updateAllowedLogin(staff.getUid(), true);
                    }else {
                        authenticationController.updateAllowedLogin(staff.getUid(), false);
                    }

                    response = buildSuccessResponse();
                } else {
                    response = buildBadRequestResponse("User update failed");
                }
            } else {
                response = buildConflictResponse("User not exists");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }


    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteStaff(@PathParam("uid") String uid) {
        final String methodName = "deleteStaff";
        start(methodName);

        Response response;
        log.info(methodName, "Delete staff (" + uid + ")");

        boolean validStaff = staffController.validateStaff(uid);
        log.debug(methodName, "Staff validation : " + validStaff);

        if (validStaff) {
            boolean deleted = staffController.delete(uid);
            log.debug(methodName, "Staff deletion : " + deleted);
            if (deleted) {
                authenticationController.deleteAuthentication(uid);
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("User deletion failed");
            }
        } else {
            response = buildBadRequestResponse("User not found");
        }
        completed(methodName);
        return response;
    }

    private void sendCreateUserEmail(Staff staff) {

        String subject = "Welcome, {fullName}! Set Up Your New Account Password";
        subject = subject.replace("{fullName}", staff.getFirstname());
        String template = FileHelper.readFileFromResources("create-account-template.txt");
        String resetPasswordLink = getProperty(Property.PASSWORD_RESET_LINK_FORMAT);
        String body = template
                .replace("{fullName}", staff.getFirstname())
                .replace("{resetLink}", resetPasswordLink)
                .replace("{userEmail}", staff.getEmail());

        emailController.send(staff.getEmail(), subject, body);
    }

/*    */

    /**
     * Deletes a user from the database.
     * <p>
     * This function takes a user UID as a parameter and deletes the user from the database.
     * If the user does not exist in the database, the function will return a 400 Bad Request
     * with a message indicating that the user was not found. If the user deletion is successful,
     * the function will return a 200 OK response. Otherwise, the function will return a 400 Bad
     * Request with a message indicating that the user deletion failed.
     *
     * @param uid The user UID to delete.
     * @return A response indicating the deletion status.
     */
/*

    @POST
    @Path("status")
    @PermitAll
    public Response updateStatus(UserStatusRequest request) {
        final String methodName = "updateStatus";
        start(methodName);

        Response response;
        log.info(methodName, "Update user status (" + request.getUid() + ") : " + request.isStatus());

        boolean validPayload = validator.updateStatus(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            // First we need to check if the user exists in the database. If the user does not exist,
            // we will return a 400 Bad Request with a message indicating that the user was not found.
            boolean userExist = staffController.validateUserUid(request.getUid());
            log.debug(methodName, "User validation : " + userExist);

            if (userExist) {
                // If the user exists, we will proceed to delete the user from the database.
                // If the deletion is successful, we will return a 200 OK response. Otherwise, we
                // will return a 400 Bad Request with a message indicating that the user deletion
                // failed.
                boolean update = staffController.updateUserStatus(request.getUid(), request.isStatus());
                log.debug(methodName, "User status update : " + update);
                if (update) {
                    response = buildSuccessResponse();
                } else {
                    response = buildBadRequestResponse("User update failed");
                }
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
    }*/
}



