package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.MasterCompanyController;
import id.tsi.mmw.controller.MasterCustomerController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class MasterCompanyService extends BaseService{
    @Inject
    private MasterCompanyController masterCompanyController;

    public MasterCompanyService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList;
        userList = masterCompanyController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }

    @POST
    public Response create(User user) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = masterCompanyController.create(user);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(User user) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        user.setUid();
        boolean result = masterCompanyController.update(user);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{CompanyID}")
    public Response delete(@PathParam("CompanyID") String CompanyID) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        User user = masterCompanyController.getUserByCompanyID(CompanyID);

        if (user.getUid() != null) {
            boolean result = masterCompanyController.delete(CompanyID);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }
}
