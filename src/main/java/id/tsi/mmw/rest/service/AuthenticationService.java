package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.AuthenticationController;
import id.tsi.mmw.controller.StaffController;
import id.tsi.mmw.controller.audit.AuditAuthenticationController;
import id.tsi.mmw.filter.ApplicationFilter;
import id.tsi.mmw.manager.EncryptionManager;
import id.tsi.mmw.model.Authentication;
import id.tsi.mmw.model.Principal;
import id.tsi.mmw.model.Staff;
import id.tsi.mmw.model.audit.AuditAuthentication;
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
import java.util.UUID;
import java.util.concurrent.ExecutorService;

@Singleton
@Path("authentications")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationService extends BaseService {

    private AuthenticationValidator validator;

    @Inject
    private AuthenticationController authenticationController;

    @Inject
    private StaffController staffController;

    @Inject
    protected ExecutorService executor;

    @Inject
    private AuditAuthenticationController auditAuthenticationController;

    public AuthenticationService() {
        log = getLogger(this.getClass());
        validator = new AuthenticationValidator();
    }

    /**
     * Handles the login functionality with authentication and session management.
     *
     * @param authRequest The authentication request containing username and password.
     * @return The response indicating the authentication status.
     */
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationRequest authRequest) {
        String methodName = "login";
        start(methodName);

        // Initialize the response with unauthorized status
        Response response = buildUnauthorizedResponse();
        String trackingId = generateTrackingID();
        boolean authenticate = false;
        String startProcessingDT = DateHelper.formatDateTime(LocalDateTime.now());

        AuditAuthentication auditAuthentication = new AuditAuthentication();
        auditAuthentication.setUid(UUID.randomUUID().toString());
        auditAuthentication.setTrackingId(trackingId);
        auditAuthentication.setUser(authRequest.getUsername());
        auditAuthentication.setEvent(Constants.EVENT_LOGIN);
        auditAuthentication.setApplication(Constants.APPLICATION_NAME);
        auditAuthentication.setCreatedDt(startProcessingDT);

        // Validate the incoming authentication request
        boolean validRequest = validator.validate(authRequest);
        log.debug(methodName, "Request payload validation : " + validRequest);

        // Get the current date and time for tracking processing start

        if (validRequest) {
            // Clone the authentication request for security purposes
            AuthenticationRequest cloneRequest = new AuthenticationRequest();
            cloneRequest.setUsername(authRequest.getUsername());
            cloneRequest.setPassword("********");

            // Log the cloned request for debugging
            log.info(methodName, JsonHelper.toJson(cloneRequest));

            // Validate if the username is a valid user
            boolean validUser = staffController.validateEmail(authRequest.getUsername());
            log.info(methodName, "Validate user : " + validUser);

            if (validUser) {
                // Retrieve user details if the user is valid
                Authentication authentication = authenticationController.getAuthenticationUser(authRequest.getUsername());

                if (authentication.getUid() != null) {

                    if (authentication.isLoginAllowed()) {
                        String hashPassword = EncryptionManager.getInstance().hash(authRequest.getPassword(), authentication.getSalt());

                        authenticate = authentication.getPassword().equals(hashPassword);
                        log.info("Authentication : " + authenticate);

                        if (authenticate) {
                            Staff staff = staffController.getStaff(authentication.getUid());
                            // Update user login timestamp upon successful authentication
                            log.info("Update last login timestamp");
                            authenticationController.updateLoginTimestamp(staff.getUid(), startProcessingDT);

                            // Clear any existing session
                            clearSession();

                            // Create a new session and set session attributes
                            Principal principal = new Principal(authRequest.getUsername());
                            setSessionAttribute(Constants.SESSION_USER, staff);
                            setSessionAttribute(ApplicationFilter.SESSION_KEY, principal);
                            setSessionAttribute(Principal.class.getCanonicalName(), principal);
                            setTrackingID(trackingId);

                            // Build success response upon successful authentication
                            response = buildSuccessResponse();
                            auditAuthentication.setMessage("");
                        }
                        else {
                            auditAuthentication.setMessage(Constants.MESSAGE_INVALID_LOGIN);
                        }
                    } else {
                        response = buildAccessDeniedResponse(Constants.MESSAGE_LOGIN_NOT_ALLOWED);
                        auditAuthentication.setMessage(Constants.MESSAGE_LOGIN_NOT_ALLOWED);
                    }
                }
            }
        }else {
            auditAuthentication.setMessage(Constants.MESSAGE_INVALID_REQUEST);
        }

        // Insert audit event
        auditAuthentication.setResult(authenticate);
        insertAuditAuthentication(auditAuthentication);

        // Log the response entity and method completion
        log.debug(methodName, response.getEntity());
        completed(methodName);

        return response;
    }

    /**
     * Logs out the user by clearing the session and redirecting to the login page.
     *
     * @return A temporary redirect response to the login page.
     */
    @GET
    @Path("logout")
    @PermitAll
    public Response logout() {
        final String methodName = "logout";
        start(methodName);

        String trackingId = getSessionAttribute(Constants.SESSION_TRACKING_ID, String.class);
        Staff staff = getSessionAttribute(Constants.SESSION_USER, Staff.class);
        String startProcessingDT = DateHelper.formatDateTime(LocalDateTime.now());

        AuditAuthentication auditAuthentication = new AuditAuthentication();
        auditAuthentication.setUid(UUID.randomUUID().toString());
        auditAuthentication.setTrackingId(trackingId);
        auditAuthentication.setUser(staff.getEmail());
        auditAuthentication.setEvent(Constants.EVENT_LOGOUT);
        auditAuthentication.setApplication(Constants.APPLICATION_NAME);
        auditAuthentication.setCreatedDt(startProcessingDT);
        auditAuthentication.setResult(true);
        auditAuthentication.setMessage("");

        // Insert audit event
        insertAuditAuthentication(auditAuthentication);

        // clearing the login session
        clearSession();

        completed(methodName);
        // send a redirect to the login page
        return Response.temporaryRedirect(URI.create(httpServletRequest.getContextPath() + "/login")).build();
    }

    @GET
    @Path("session")
    @PermitAll
    public Response session() {
        final String methodName = "session";
        start(methodName);

        completed(methodName);
        return buildSuccessResponse();
    }

    @GET
    @Path("profile")
    @PermitAll
    public Response getLoginProfile() {
        final String methodName = "getLoginProfile";
        start(methodName);

        Staff staff = getSessionAttribute(Constants.SESSION_USER, Staff.class);
        completed(methodName);
        return buildSuccessResponse(staff);
    }

    private void insertAuditAuthentication(AuditAuthentication audit) {
        executor.execute(() -> {
            auditAuthenticationController.insertAuditAuthentication(audit);
        });
    }

}
