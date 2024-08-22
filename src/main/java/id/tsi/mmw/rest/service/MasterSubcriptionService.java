package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.MasterSettingController;
import id.tsi.mmw.controller.MasterSubcriptionController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class MasterSubcriptionService extends BaseService{
    @Inject
    private MasterSubcriptionController masterSubcriptionController;

    public MasterSubcriptionService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList;
        userList = masterSubcriptionController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    @POST
    public Response create(User user) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = masterSubcriptionController.create(user);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = masterSubcriptionController.update(user);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{sub_id}")
    public Response delete(@PathParam("sub_id") String sub_id) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = masterSubcriptionController.getUserByUid(sub_id);

        if (user.getUid() != null) {
            boolean result = masterSubcriptionController.delete(sub_id);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }
}
