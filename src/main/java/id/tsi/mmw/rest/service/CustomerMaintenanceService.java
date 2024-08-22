package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.CustomerMaintenanceController;
import id.tsi.mmw.controller.MasterSalesLevelController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class CustomerMaintenanceService extends BaseService {
    @Inject

    private CustomerMaintenanceController customerMaintenanceController;

    public CustomerMaintenanceService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList;
        userList = customerMaintenanceController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    @POST
    public Response create(User user) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = customerMaintenanceController.create(user);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = customerMaintenanceController.update(user);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{customer_id}")
    public Response delete(@PathParam("customer_id") String customer_id) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = customerMaintenanceController.getUserByCustomerID(customer_id);

        if (user.getUid() != null) {
            boolean result = customerMaintenanceController.delete(customer_id);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }
}
