package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.MasterUserGroupController;
import id.tsi.mmw.model.Staff;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;
import java.util.List;

public class MasterUserGroupService extends BaseService {

    @Inject
    private MasterUserGroupController masterUserGroupController;

    public MasterUserGroupService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<Staff> staffList;
        staffList = masterUserGroupController.getUserList();
        completed(methodName);
        return buildSuccessResponse(staffList);
    }

    @POST
    public Response create(Staff staff) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        boolean result = masterUserGroupController.create(staff);
        completed(methodName);
        return response;
    }

    @PUT
    public Response update(Staff staff) {
        final String methodName = "update";
        Response response = buildSuccessResponse();
        start(methodName);
        boolean result = masterUserGroupController.update(staff);

        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    public Response delete(@PathParam("uid") String uid) {
        final String methodName = "delete";
        start(methodName);

        Response response = buildBadRequestResponse();
        Staff staff = masterUserGroupController.getUserByUid(uid);

        if (staff.getUid() != null) {
            boolean result = masterUserGroupController.delete(uid);
            if (result) {
                response = buildSuccessResponse();
            }
        }
        completed(methodName);
        return response;


    }
    
}
