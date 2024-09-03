package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.MasterSalesController;
import id.tsi.mmw.controller.MasterSalesLevelController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class MasterSalesService extends BaseService{
    @Inject
    private MasterSalesController masterSalesController;

    public MasterSalesService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList;
        userList = masterSalesController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    @POST
    public Response create(User user) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = masterSalesController.create(user);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = masterSalesController.update(user);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{sale_id}")
    public Response delete(@PathParam("sale_id") String sale_id) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = masterSalesController.getUserBySaleId(sale_id);

        if (user.getUid() != null) {
            boolean result = masterSalesController.delete(sale_id);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }
}
