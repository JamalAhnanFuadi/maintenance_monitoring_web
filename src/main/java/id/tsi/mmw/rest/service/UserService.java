package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.UserController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService extends BaseService {

    @Inject
    private UserController userController;

    public UserService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList = userController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
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
    public Response create(User user) {

        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = userController.create(user);
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




