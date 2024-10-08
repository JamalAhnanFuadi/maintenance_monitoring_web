package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.AuthenticationController;
import id.tsi.mmw.controller.UserController;
import id.tsi.mmw.manager.EncryptionManager;
import id.tsi.mmw.model.Authentication;
import id.tsi.mmw.model.User;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.property.Property;
import id.tsi.mmw.rest.model.request.PasswordRequest;
import id.tsi.mmw.rest.validator.PasswordValidator;
import id.tsi.mmw.util.helper.DateHelper;
import id.tsi.mmw.util.json.JsonHelper;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;

@Singleton
@Path("passwords")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PasswordService extends BaseService {

    @Inject
    private UserController userController;
    @Inject
    private AuthenticationController authenticationController;
    private PasswordValidator validator;

    public PasswordService() {
        log = getLogger(this.getClass());
        validator = new PasswordValidator();
    }

    /**
     * Handles the change password request by validating the request payload, retrieving the user's detail,
     * checking if the user already has an authentication record in the database, generating a new salt value
     * for the user, hashing the new password with the salt value, creating a new authentication record with the
     * new password hash, inserting the new authentication record into the database, and updating the user's status
     * to enabled.
     *
     * @param request The password request containing the user's email and new password
     * @return The response indicating the change password status
     */
    @POST
    @Path("change")
    public Response changePassword(PasswordRequest request) {
        final String methodName = "changePassword";
        start(methodName);

        Response response;
        log.info(methodName, "Change user password");

        // Validate the request payload
        boolean validRequest = validator.validate(request);
        log.debug(methodName, "Request payload validation : " + validRequest);
        if (validRequest) {
            // Clone the authentication request for security purposes
            PasswordRequest cloneRequest = new PasswordRequest();
            cloneRequest.setEmail(request.getEmail());
            cloneRequest.setPassword("********");
            log.info(methodName, JsonHelper.toJson(cloneRequest));

            // Check if the user exists in the database
            boolean validUser = userController.validateEmail(request.getEmail());
            log.debug(methodName, "Email validation : " + validUser);
            if (validUser) {
                // Get the user's detail
                User user = userController.getUserDetailByEmail(request.getEmail());

                // Check if the user already has an authentication record in the database
                boolean hasAuthentication = authenticationController.hasAuthentication(user.getUid());
                log.debug(methodName, "User has existing authentication : " + hasAuthentication);

                // Generate a new salt value for the user
                String salt = "";
                if (hasAuthentication) {
                    // Get the existing salt value from the database
                    salt = authenticationController.getUserSalt(user.getUid());
                    log.debug(methodName, "Retrieve existing salt value : " + salt);
                } else {
                    // Generate a new salt value for the user
                    salt = EncryptionManager.getInstance().generateRandomString(getIntegerProperty(Property.ENCRYPTION_SALT_LENGTH));
                    log.debug(methodName, "Generate new salt value : " + salt);
                }

                // Hash the new password with the salt value
                String hashedPassword = EncryptionManager.getInstance().hash(request.getPassword(), salt);
                log.debug(methodName, "Hash the new password with the salt value : " + hashedPassword);

                // Create a new authentication record with the new password hash
                String processingTime = DateHelper.formatDBDateTime(LocalDateTime.now());
                Authentication authentication = new Authentication();
                authentication.setUid(user.getUid());
                authentication.setSalt(salt);
                authentication.setPasswordHash(hashedPassword);
                authentication.setLoginAllowed(true);
                authentication.setLastPasswordSet(processingTime);
                authentication.setCreateDt(processingTime);

                // Insert the new authentication record into the database
                boolean createAuth = authenticationController.createAuthentication(authentication);
                log.debug(methodName, "Create authentication : " + createAuth);
                if (createAuth) {
                    // Update the user's status to enabled
                    userController.updateUserStatus(user.getUid(), true);
                    response = buildSuccessResponse();
                } else {
                    response = buildBadRequestResponse("Failed to create authentication");
                }

            } else {
                response = buildBadRequestResponse("User not found");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }
}
