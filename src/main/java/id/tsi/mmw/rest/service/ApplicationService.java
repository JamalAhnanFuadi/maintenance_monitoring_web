package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.ApplicationController;
import id.tsi.mmw.model.Application;
import id.tsi.mmw.model.Pagination;
import id.tsi.mmw.model.User;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.PaginationRequest;
import id.tsi.mmw.rest.model.response.UserPaginationResponse;
import id.tsi.mmw.rest.validator.AuthenticationValidator;
import id.tsi.mmw.util.json.JsonHelper;

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
@Path("applications")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationService extends BaseService{

    @Inject
    private ApplicationController applicationController;
    public ApplicationService() {
        log = getLogger(this.getClass());
    }

    @PermitAll
    @GET
    public Response getApplicationList() {
        final String methodName = "getApplicationList";
        start(methodName);
        log.info(methodName, "Get Application List");
        List<Application> applications = applicationController.getApplicationList();
        completed(methodName);
        return buildSuccessResponse(applications);
    }
}
