package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.MasterServiceController;
import id.tsi.mmw.controller.MasterSettingController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class MasterServiceService extends BaseService{
    @Inject
    private MasterServiceController masterServiceController;

    public MasterServiceService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList;
        userList = masterServiceController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    @POST
    public Response create(User user) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = masterServiceController.create(user);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = masterServiceController.update(user);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{service_id}")
    public Response delete(@PathParam("service_id") String uid) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = masterServiceController.getUserByUid(uid);

        if (user.getUid() != null) {
            boolean result = masterServiceController.delete(uid);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }

}
