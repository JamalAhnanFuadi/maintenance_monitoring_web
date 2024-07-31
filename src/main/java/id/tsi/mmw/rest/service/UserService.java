package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.UserController;
import id.tsi.mmw.model.User;
import id.tsi.mmw.rest.validator.UserValidator;
import id.tsi.mmw.util.helper.DateHelper;

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

    private UserValidator validator;

    public UserService() {
        log = getLogger(this.getClass());
        validator = new UserValidator();
    }

    /**
     * This method is a REST API endpoint that returns a list of users.
     * It is accessible to all users (@PermitAll).
     *
     * @return a Response object that contains a JSON representation of the user list.
     */
    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        // Retrieve the list of users from the UserController
        List<User> userList = userController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    /**
     * This method is a REST API endpoint that retrieves user details by UID.
     * It is accessible to all users (@PermitAll).
     *
     * @param uid The UID of the user to retrieve details for.
     * @return A Response object containing the user details, or a BadRequestResponse if the UID is invalid.
     */
    @GET
    @PermitAll
    @Path("{uid}")
    public Response getUserDetail(@PathParam("uid") String uid) {
        final String methodName = "getUserDetail";
        start(methodName);

        // Initialize the response object with a BadRequest status
        Response response = buildBadRequestResponse();

        // Retrieve the user details from the UserController by UID
        User user = userController.getUserByUid(uid);

        // TODO to fetch other user details from child tables
        // If the user details are found, update the response with the user details and set the status to Success
        if (user.getUid() != null) {
            response = buildSuccessResponse(user);
        }
        completed(methodName);
        return response;
    }


    @POST
    public Response create(User request) {
        final String methodName = "create";
        start(methodName);
        Response response = buildBadRequestResponse();

        boolean requestValidation = validator.validate(request);
        log.debug(methodName, "Request validation : " + requestValidation);
        if (requestValidation) {
            String uid = UUID.randomUUID().toString();
            String createdDt = DateHelper.formatFileDateTime(LocalDateTime.now());

            request.setUid(uid);

            //boolean result = userController.create(user);
        }

        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = userController.update(user);

        completed(methodName);
        return response;
    }

    /**
     * This method handles the HTTP DELETE request for a user by UID.
     * It is mapped to the "/users/{uid}" path.
     *
     * @param uid The UID of the user to be deleted.
     * @return The HTTP response indicating the success or failure of the deletion.
     */
    @DELETE
    @Path("{uid}")
    public Response delete(@PathParam("uid") String uid) {
        final String methodName = "delete";
        start(methodName);

        // Initialize the response with a BadRequest status
        Response response = buildBadRequestResponse();

        // Retrieve the user details from the UserController by UID
        User user = userController.getUserByUid(uid);

        // Check if the user details are found
        if (user.getUid() != null) {
            // Delete the user from the database
            boolean result = userController.delete(uid);

            // Check if the deletion was successful
            if (result) {
                // Update the response with a Success status
                response = buildSuccessResponse();
            }
        }

        completed(methodName);
        return response;

    }

}



