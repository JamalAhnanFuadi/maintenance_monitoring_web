package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.AccessMatricController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class AccessMatricService extends BaseService{
    @Inject

    private AccessMatricController accessMatricConntroller;

    public AccessMatricService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList;
        userList = accessMatricConntroller.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    @POST
    public Response create(User user) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = accessMatricConntroller.create(user);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = accessMatricConntroller.update(user);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{user_id}")
    public Response delete(@PathParam("user_id") String user_id) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = accessMatricConntroller.getUserByUid(user_id);

        if (user.getUid() != null) {
            boolean result = accessMatricConntroller.delete(user_id);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }
}
