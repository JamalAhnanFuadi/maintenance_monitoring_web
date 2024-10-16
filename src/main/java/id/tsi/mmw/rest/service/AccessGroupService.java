package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.AccessGroupController;
import id.tsi.mmw.controller.ApplicationController;
import id.tsi.mmw.model.AccessGroup;
import id.tsi.mmw.model.Application;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("accessgroup")
@Produces(MediaType.APPLICATION_JSON)
public class AccessGroupService extends BaseService{

    @Inject
    private AccessGroupController accessGroupController;
    public AccessGroupService() {
        log = getLogger(this.getClass());
    }

    @PermitAll
    @GET
    public Response getAccessGroupList() {
        final String methodName = "getAccessGroupList";
        start(methodName);
        log.info(methodName, "Get Access Group List");
        List<AccessGroup> applications = accessGroupController.getAccessGroupList();
        completed(methodName);
        return buildSuccessResponse(applications);
    }
}
