package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.AccessMatrixController;
import id.tsi.mmw.controller.UserController;
import id.tsi.mmw.manager.EncryptionManager;
import id.tsi.mmw.model.Authentication;
import id.tsi.mmw.model.Pagination;
import id.tsi.mmw.model.User;
import id.tsi.mmw.model.UserAccessMatrix;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.property.Property;
import id.tsi.mmw.rest.model.request.PaginationRequest;
import id.tsi.mmw.rest.model.request.UserRequest;
import id.tsi.mmw.rest.model.response.UserPaginationResponse;
import id.tsi.mmw.rest.model.response.UserResponse;
import id.tsi.mmw.rest.validator.UserValidator;
import id.tsi.mmw.util.helper.DateHelper;
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

@Singleton
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService extends BaseService {

    @Inject
    private UserController userController;

    @Inject
    private AccessMatrixController accessMatrixController;

    private UserValidator validator;

    public UserService() {
        log = getLogger(this.getClass());
        validator = new UserValidator();
    }

    /**
     * Gets a list of users given a pagination request.
     *
     * This function takes a pagination request and uses it to retrieve a list of users
     * from the database. The function validates the request first, and only proceeds
     * if the request is valid. If the request is invalid, the function will return
     * HTTP 400 Bad Request.
     *
     * @param request The pagination request containing the page number, page size,
     *                search filter, and sort order.
     * @return A response containing a list of users and pagination properties.
     */
    @GET
    @PermitAll
    public Response getUserList(PaginationRequest request) {
        final String methodName = "getUserList";
        start(methodName);
        log.info(methodName, "Get User List");
        log.info(methodName, JsonHelper.toJson(request));
        Response response;

        // Validate the request payload
        boolean validateRequest = validator.validate(request);
        log.debug(methodName, "Request payload validation : " + validateRequest);

        // If the request is valid, proceed with retrieving the user list
        if(validateRequest) {
            // Retrieve the list of users from the database
            List<User> users = userController.getUserListPagination(
                    request.getPageSize(),
                    paginationOffsetBuilder(request.getPageNumber(), request.getPageSize()),
                    request.getOrderBy(),
                    request.getSortOrder(),
                    request.getSearchFilter());

            // Retrieve the total records and pages from the database
            Pagination pagination = userController.countTotalRecordsAndPages(request.getPageSize(), request.getSearchFilter());

            // Set the pagination properties in the pagination object
            pagination.setPageNumber(request.getPageNumber());
            pagination.setPageSize(request.getPageSize());
            pagination.setSearchFilter(request.getSearchFilter());
            pagination.setCurrentPages(users.size());
            pagination.setOrderBy(request.getOrderBy());
            pagination.setSortOrder(request.getSortOrder());

            // Create a new UserPaginationResponse object and set the pagination and users properties
            UserPaginationResponse usersResponse = new UserPaginationResponse();
            usersResponse.setFilter(pagination);
            usersResponse.setUsers(users);

            // Build a success response containing the user list and pagination properties
            response = buildSuccessResponse(usersResponse);
        } else {
            // Build a bad request response if the request is invalid
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }

        completed(methodName);
        return response;
    }

    /**
     * Retrieve user detail by UID.
     * <p>
     * This REST endpoint will retrieve user detail by its UID.
     * <p>
     * The REST endpoint will return HTTP 400 Bad Request if the user id is invalid.
     * <p>
     * The REST endpoint will return HTTP 200 OK if the user id is valid.
     *
     * @param uid The unique identifier of the user
     * @return The user detail response
     */
    @GET
    @PermitAll
    @Path("{uid}")
    public Response getUserDetail(@PathParam("uid") String uid) {
        final String methodName = "getUserDetail";
        start(methodName);
        Response response;

        // Log the user id that is being requested
        log.info(methodName, "Get User Detail (" + uid + ")");

        // Validate the user id by checking if it exists in the database
        boolean validateUser = userController.validateUserUid(uid);
        log.debug(methodName, "User validation : " + validateUser);

        // If the user id is valid, retrieve the user detail from the database
        if(validateUser){
            User user = userController.getUserByUid(uid);
            // fetch User access matrix information
            if(user.getAccessGroupUid() != null && !user.getAccessGroupUid().isEmpty()) {
                List<UserAccessMatrix> userAccessMatrix = accessMatrixController.getUserAccessMatrix(user.getUid(),user.getAccessGroupUid());
                user.setUserAccessMatrix(userAccessMatrix);
            }

            // Create a new UserResponse object and set the user property
            UserResponse userResponse = new UserResponse();
            userResponse.setUser(user);

            // Build a success response containing the user detail
            response = buildSuccessResponse(userResponse);
        } else {
            // Build a bad request response if the user id is invalid
            response = buildBadRequestResponse("Invalid User id");
        }

        // Log that the REST endpoint has completed
        completed(methodName);
        return response;
    }

    /**
     * Creates a new user with the given request payload.
     *
     * This function will validate the request payload first, and only proceed
     * if the payload is valid. If the payload is invalid, the function will return
     * HTTP 400 Bad Request.
     *
     * If the payload is valid, the function will check if the user email is already
     * exist in the database. If the email is already exist, the function will return
     * HTTP 409 Conflict with a message "User email already exists".
     *
     * If the email is not exist, the function will generate a user primary key,
     * generate user information to be create to database, generate user authentication
     * information, and proceed user creation to database. If the user creation is
     * successful, the function will return HTTP 201 Created with a message "User created".
     * Otherwise, the function will return HTTP 400 Bad Request with a message "User creation failed".
     *
     * @param request The request payload containing the user information.
     * @return A response containing the result of the user creation.
     */
    @POST
    public Response create(UserRequest request) {
        final String methodName = "create";
        Response response = null;
        start(methodName);
        log.info(methodName, "Create User");
        log.info(methodName, JsonHelper.toJson(request));

        // validate request payload. This is done by calling the validator.create() method,
        // which will validate the request payload and return true if the payload is valid,
        // otherwise false.
        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        // if payload is valid, continue proceed,
        // else return bad request
        if(validPayload) {
            // validate if user email is already exist. This is done by calling the
            // userController.validateEmail() method, which will validate if the user email
            // is already exist in the database and return true if the email is already exist,
            // otherwise false.
            boolean userExist = userController.validateEmail(request.getEmail());
            log.debug(methodName, "Email validation : " + userExist);

            // if user not exist, continue user creation process
            // else return user already exist response
            if(!userExist) {
                // generate user primary key. This is done by generating a UUID string.
                String uuid = UUID.randomUUID().toString();

                // Generate user information to be create to database.
                // This is done by creating a new User object and set the properties:
                // uid, firstname, lastname, fullname, email, mobile number, date of birth
                User user = new User();
                user.setUid(uuid);
                user.setFirstname(request.getFirstname());
                user.setLastname(request.getLastname());

                // Concat fistname and lastname to fullname if last name is not empty
                if(request.getLastname() != null || !request.getLastname().isEmpty()) {
                    user.setFullname(request.getFirstname() + " " + request.getLastname());
                }
                else {
                    user.setFirstname(request.getFirstname());
                }

                user.setEmail(request.getEmail());
                user.setMobileNumber(request.getMobileNumber());
                LocalDate dobLD = DateHelper.parseDate(request.getDob());
                LocalDateTime dobLDT = dobLD.atStartOfDay();
                user.setDob(DateHelper.formatDBDateTime(dobLDT));

                String processingTime = DateHelper.formatDateTime(LocalDateTime.now());
                user.setCreateDt(processingTime);
                user.setModifyDt(processingTime);

                // generate user authentication information. This is done by generating
                // a salt string, hashing the default password with the salt, and creating
                // a new Authentication object and set the properties: uid, salt, passwordHash,
                // loginAllowed, and createDt.
                String salt = EncryptionManager.getInstance().generateRandomString(getIntegerProperty(Property.ENCRYPTION_SALT_LENGTH));
                String defaultPassword = getProperty(Property.USER_DEFAULT_PASSWORD);
                String hashedPassword = EncryptionManager.getInstance().hash(defaultPassword, salt);

                Authentication authentication = new Authentication();
                authentication.setUid(uuid);
                authentication.setSalt(salt);
                authentication.setPasswordHash(hashedPassword);
                authentication.setLoginAllowed(false);
                authentication.setCreateDt(processingTime);

                // proceed user creation to database
                boolean created = userController.create(user, authentication);
                if(created) {
                    // TO DO send email to user after user created to activate login and change the password
                    response = buildSuccessResponse();
                }else {
                    response = buildBadRequestResponse("User creation failed");
                }
            }else {
                response = buildConflictResponse("User email already exists");
            }
        }else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);

        boolean result = userController.update(user);

        completed(methodName);
        return response;
    }

    /**
     * Deletes a user from the database.
     *
     * This function takes a user UID as a parameter and deletes the user from the database.
     * If the user does not exist in the database, the function will return a 400 Bad Request
     * with a message indicating that the user was not found. If the user deletion is successful,
     * the function will return a 200 OK response. Otherwise, the function will return a 400 Bad
     * Request with a message indicating that the user deletion failed.
     *
     * @param uid The user UID to delete.
     * @return A response indicating the deletion status.
     */
    @DELETE
    @Path("{uid}")
    public Response delete(@PathParam("uid") String uid) {
        final String methodName = "delete";
        start(methodName);

        Response response;
        log.info(methodName, "Delete User (" + uid + ")");

        // First we need to check if the user exists in the database. If the user does not exist,
        // we will return a 400 Bad Request with a message indicating that the user was not found.
        boolean userExist = userController.validateUserUid(uid);
        log.debug(methodName, "User validation : " + userExist);

        if (userExist) {
            // If the user exists, we will proceed to delete the user from the database.
            // If the deletion is successful, we will return a 200 OK response. Otherwise, we
            // will return a 400 Bad Request with a message indicating that the user deletion
            // failed.
            boolean deleted = userController.delete(uid);
            log.debug(methodName, "User deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("User deletion failed");
            }
        } else {
            // If the user does not exist, we will return a 400 Bad Request with a message
            // indicating that the user was not found.
            response = buildBadRequestResponse("User not found");
        }
        completed(methodName);
        return response;
    }
}



