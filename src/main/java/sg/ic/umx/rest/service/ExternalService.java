package sg.ic.umx.rest.service;

import sg.ic.umx.controller.ApplicationController;
import sg.ic.umx.controller.BusinessRoleController;
import sg.ic.umx.controller.ExecutionController;
import sg.ic.umx.controller.RuleViolationController;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Execution;
import sg.ic.umx.model.ExecutionStatus;
import sg.ic.umx.rest.model.ApplicationListResponse;
import sg.ic.umx.rest.model.ApplicationViolationResponse;
import sg.ic.umx.rest.model.BusinessRoleListResponse;
import sg.ic.umx.rest.security.APIKey;

import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Singleton
@APIKey
@Path("external")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ExternalService extends BaseService {

    private final ApplicationController applicationController;

    private final ExecutionController executionController;

    private final RuleViolationController violationController;

    private final BusinessRoleController businessRoleController;

    public ExternalService() {
        log = getLogger(this.getClass());
        executionController = new ExecutionController();
        violationController = new RuleViolationController();
        applicationController = new ApplicationController();
        businessRoleController = new BusinessRoleController();
    }

    @GET
    @Path("businessroles")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listBusinessRoles() {
        final String methodName = "listBusinessRoles";
        start(methodName);
        BusinessRoleListResponse response = new BusinessRoleListResponse();
        response.setRoleList(businessRoleController.list());

        completed(methodName);
        return buildSuccessResponse(response);
    }

    @GET
    @Path("applications")
    public Response list() {
        final String methodName = "list";
        start(methodName);
        List<Application> appList = applicationController.list().stream().map(app -> {
            Application application = new Application(app.getId(), app.getName());
            application.setStatus(app.getStatus());
            return application;
        }).collect(Collectors.toList());
        ApplicationListResponse response = new ApplicationListResponse(appList);
        completed(methodName);
        return buildSuccessResponse(response);
    }

    @GET
    @Path("applications/{applicationId}/violations")
    public Response getApplicationViolations(@PathParam("applicationId") final long applicationId) {
        final String methodName = "getApplicationViolations";
        Response response = buildResourceNotFoundResponse();
        start(methodName);

        // Validate Application ID
        Application application = applicationController.get(applicationId);

        if (application != null) {
            // Get Latest Execution
            ApplicationViolationResponse violationResponse = new ApplicationViolationResponse();

            Execution execution =
                    executionController.getLatestExecutionByApplicationId(applicationId, ExecutionStatus.COMPLETED);

            if (execution != null) {
                violationResponse.setExecution(execution);
                violationResponse.setViolationList(violationController.listByExecutionId(execution.getId()));
            } else {
                violationResponse.setViolationList(new ArrayList<>());
            }
            response = buildSuccessResponse(violationResponse);
        }

        completed(methodName);
        return response;
    }

    @GET
    @Path("applications/{applicationId}/appConfig")
    public Response getApplicationConfigurationName(@PathParam("applicationId") final long applicationId) {
        final String methodName = "getApplicationConfigurationName";
        Response response = buildResourceNotFoundResponse();
        start(methodName);

        // Validate Application ID
        Application application = applicationController.get(applicationId);

        if (application != null) {
            response = buildSuccessResponse(application);
        }

        completed(methodName);
        return response;
    }
}
