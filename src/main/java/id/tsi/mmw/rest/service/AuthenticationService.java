package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.AuthenticationController;
import id.tsi.mmw.controller.UserController;
import id.tsi.mmw.filter.ApplicationFilter;
import id.tsi.mmw.manager.EncryptionManager;
import id.tsi.mmw.model.Principal;
import id.tsi.mmw.model.User;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.AuthenticationRequest;
import id.tsi.mmw.rest.validator.AuthenticationValidator;
import id.tsi.mmw.util.helper.DateHelper;
import id.tsi.mmw.util.json.JsonHelper;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.time.LocalDateTime;

@Singleton
@Path("authentication")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationService extends BaseService {

    private AuthenticationValidator validator;

    @Inject
    private AuthenticationController authenticationController;

    @Inject
    private UserController userController;

    public AuthenticationService() {
        log = getLogger(this.getClass());
        validator = new AuthenticationValidator();
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationRequest authRequest) {
        String methodName = "login";
        start(methodName);

        // Initialize the response with unauthorized status
        Response response = buildUnauthorizedResponse();

        // Validate the incoming authentication request
        boolean validRequest = validator.validate(authRequest);
        log.debug(methodName, "Request payload validation : " + validRequest);

        // Get the current date and time for tracking processing start
        String startProcessingDT = DateHelper.formatDateTime(LocalDateTime.now());

        if (validRequest) {
            // Clone the authentication request for security purposes
            AuthenticationRequest cloneRequest = new AuthenticationRequest();
            cloneRequest.setUsername(authRequest.getUsername());
            cloneRequest.setPassword("********");

            // Log the cloned request for debugging
            log.info(methodName, JsonHelper.toJson(cloneRequest));

            // Initialize variables for user authentication
            boolean authenticate = false;
            String userId = null;
            User user = null;
            String trackingId = generateTrackingID();

            // Validate if the username is a valid root user
            boolean validUser = userController.validateEmail(authRequest.getUsername());
            log.info(methodName, "Validate user : " + validUser);

            if (validUser) {

                user = userController.getUserDetailByEmail(authRequest.getUsername());

                if (user != null) {
                    // Retrieve user salt and hash the password for comparison
                    String userSalt = authenticationController.getUserSalt(user.getUid());
                    String hashPassword = EncryptionManager.getInstance().hash(authRequest.getPassword(), userSalt);

                    // Authenticate the root user based on username and hashed password
                    authenticate = authenticationController.authenticateUser(authRequest.getUsername(), hashPassword);
                    log.info("Authenticate username and password : " + authenticate);

                    if (authenticate) {

                        userId = user.getUid();

                        log.info("Update user login timestamp");
                        authenticationController.updateLoginTimestamp(userId, startProcessingDT);

                        // Clear any existing session
                        clearSession();

                        // Create a new session and set session attributes
                        Principal principal = new Principal(authRequest.getUsername());
                        setSessionAttribute(Constants.SESSION_USER, user);
                        setSessionAttribute(ApplicationFilter.SESSION_KEY, principal);
                        setSessionAttribute(Principal.class.getCanonicalName(), principal);
                        setTrackingID(trackingId);

                        // Build success response
                        response = buildSuccessResponse();
                    }
                }
            }
        }

        // TODO: Implement audit authentication log


        // Log the response entity and method completion
        log.debug(methodName, response.getEntity());
        completed(methodName);

        return response;
    }

    @GET
    @Path("logout")
    @PermitAll
    public Response logout() {
        clearSession();
        return Response.temporaryRedirect(URI.create(httpServletRequest.getContextPath() + "/login")).build();
    }

    @GET
    @Path("session")
    @PermitAll
    public Response session() {
        return buildSuccessResponse();
    }
}
