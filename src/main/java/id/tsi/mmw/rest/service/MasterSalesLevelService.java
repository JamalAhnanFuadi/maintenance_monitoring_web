package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.MasterProductController;
import id.tsi.mmw.controller.MasterSalesLevelController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class MasterSalesLevelService extends BaseService {
    @Inject
    private MasterSalesLevelController masterSalesLevelController;

    public MasterSalesLevelService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList;
        userList = masterSalesLevelController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    @POST
    public Response create(User user) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = masterSalesLevelController.create(user);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = masterSalesLevelController.update(user);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    public Response delete(@PathParam("uid") String uid) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = masterSalesLevelController.getUserByUid(uid);

        if (user.getUid() != null) {
            boolean result = masterSalesLevelController.delete(uid);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }

}
