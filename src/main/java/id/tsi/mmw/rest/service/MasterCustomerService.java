package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.MasterCustomerController;

import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class MasterCustomerService extends BaseService{
    @Inject
    private MasterCustomerController masterCustomerController;

    public MasterCustomerService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList;
        userList = masterCustomerController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    @POST
    public Response create(User user) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = masterCustomerController.create(user);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = masterCustomerController.update(user);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{CustomerID}")
    public Response delete(@PathParam("CustomerID") String CustomerID) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = masterCustomerController.getUserByCustomerID(CustomerID);

        if (user.getUid() != null) {
            boolean result = masterCustomerController.delete(CustomerID);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }

}
