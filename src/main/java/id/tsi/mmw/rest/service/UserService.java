package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.UserController;
import id.tsi.mmw.manager.EncryptionManager;
import id.tsi.mmw.model.Authentication;
import id.tsi.mmw.model.Pagination;
import id.tsi.mmw.model.User;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.property.Property;
import id.tsi.mmw.rest.model.request.PaginationRequest;
import id.tsi.mmw.rest.model.request.UserRequest;
import id.tsi.mmw.rest.model.response.UserPaginationResponse;
import id.tsi.mmw.rest.validator.UserValidator;
import id.tsi.mmw.util.helper.DateHelper;
import id.tsi.mmw.util.json.JsonHelper;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Singleton
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService extends BaseService {

    @Inject
    private UserController userController;

    private UserValidator validator;

    public UserService() {
        log = getLogger(this.getClass());
        validator = new UserValidator();
    }

    @GET
    @PermitAll
    public Response getUserList(PaginationRequest request) {
        final String methodName = "getUserList";
        start(methodName);
        log.info(methodName, "Get User List");
        log.info(methodName, JsonHelper.toJson(request));
        Response response;

        boolean validateRequest = validator.validate(request);
        log.debug(methodName, "Request payload validation : " + validateRequest);
        if(validateRequest) {

            List<User> users = userController.getUserListPagination(
                    request.getPageSize(),
                    paginationOffset(request.getPageNumber(), request.getPageSize()),
                    request.getOrderBy(),
                    request.getSortOrder(),
                    request.getSearchFilter());

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

            response = buildSuccessResponse(usersResponse);
        }else {
            response =  buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @GET
    @PermitAll
    @Path("{uid}")
    public Response getUserDetail(@PathParam("uid") String uid) {
        final String methodName = "getUserDetail";
        start(methodName);
        Response response = buildBadRequestResponse();

        User user = userController.getUserByUid(uid);

        if(user.getUid() != null) {
            response = buildSuccessResponse(user);
        }

        completed(methodName);
        return response;
    }

    @POST
    public Response create(UserRequest request) {
        final String methodName = "create";
        Response response = null;
        start(methodName);
        log.info(methodName, "Create User");
        log.info(methodName, JsonHelper.toJson(request));

        // validate request payload,
        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        // if payload is valid, continue proceed,
        // else return bad request
        if(validPayload) {
            // validate if user email is already exist
            boolean userExist = userController.validateEmail(request.getEmail());
            log.debug(methodName, "Email validation : " + userExist);

            // if user not exist, continue user creation process
            // else return user already exist response
            if(!userExist) {
                // generate user primary key
                String uuid = UUID.randomUUID().toString();

                // Generate user information to be create to database
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
                user.setDob(request.getDob());

                String processingTime = DateHelper.formatDateTime(LocalDateTime.now());
                user.setCreateDt(processingTime);
                user.setModifyDt(processingTime);

                // generate user authentication information
                String salt = EncryptionManager.getInstance().generateRandomString(getIntegerProperty(Property.ENCRYPTION_SALT_LENGTH));
                String defaultPassword = getProperty(Property.USER_DEFAULT_PASSWORD);
                String hashedPassword = EncryptionManager.getInstance().hash(defaultPassword, salt);

                Authentication authentication = new Authentication();
                authentication.setUid(uuid);
                authentication.setSalt(salt);
                authentication.setPasswordHash(hashedPassword);
                authentication.setLoginAllowed(false);
                authentication.setCreateDt(processingTime);
                authentication.setLastPasswordSet(processingTime);

                // proceed user creation to database



            }else {

            }
        }else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

/*    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = userController.update(user);

        completed(methodName);
        return response;
    }*/

    @DELETE
    @Path("{uid}")
    public Response delete(@PathParam("uid")String uid) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = userController.getUserByUid(uid);

        if(user.getUid() != null) {
            boolean result = userController.delete(uid);
            if(result)
            {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;

    }
}



