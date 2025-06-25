package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.ProjectController;
import id.tsi.mmw.model.*;
import id.tsi.mmw.rest.validator.CustomerValidator;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;

@Singleton
@Path("projects")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectService extends BaseService {

    @Inject
    private ProjectController projectController;

    private CustomerValidator validator;

    public ProjectService() {
        log = getLogger(this.getClass());
        validator = new CustomerValidator();
    }

    @PermitAll
    @GET
    public Response getProjectList() {
        final String methodName = "getProjectList";
        start(methodName);
        log.info(methodName, "Get Project List");

        List<Project> projects = projectController.getProjectList();

        for (Project project : projects) {
            List<ProjectTag> tags = projectController.getProjectTagList(project.getUid());
            project.setProjectTags(tags);

            List<ProjectStaffPIC> staffPICs = projectController.getStaffPICList(project.getUid());
            project.setStaffPic(staffPICs);
        }

        List<Project> result = new ArrayList<>(projects);

        completed(methodName);
        return buildSuccessResponse(result);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getProject(@PathParam("uid") String uid) {
        final String methodName = "getProject";
        start(methodName);

        Response response;
        log.info(methodName, "Get Project (" + uid + ")");

        boolean isExist = projectController.validateProject(uid);
        log.debug(methodName, "Project validation : " + isExist);

        if (isExist) {
            Project project = projectController.getProjectByUid(uid);

            List<ProjectStaffPIC> staffPICs = projectController.getStaffPICList(project.getUid());
            project.setStaffPic(staffPICs);

            List<ProjectCustomerPIC> customerPICs = projectController.getCustomerPICList(project.getUid());
            project.setCustomerPic(customerPICs);

            response = buildSuccessResponse(project);
        } else {
            response = buildBadRequestResponse("Project ID not found");
        }
        completed(methodName);
        return response;
    }

    @GET
    @Path("/service/{uid}")
    @PermitAll
    public Response getProjectService(@PathParam("uid") String uid) {
        final String methodName = "getProjectService";
        start(methodName);

        Response response;
        log.info(methodName, "Get Project Service (" + uid + ")");

        boolean isExist = projectController.validateProject(uid);
        log.debug(methodName, "Project validation : " + isExist);

        if (isExist) {
            List<ProjectServiceOrder> customerPICs = projectController.getServiceOrderList(uid);
            for (ProjectServiceOrder projectServiceOrder : customerPICs) {
                List<ProjectTag> projectServiceOrderDetails = projectController.getProjectTagListByServiceOrder(uid, projectServiceOrder.getUid());
                projectServiceOrder.setProjectTags(projectServiceOrderDetails);
            }

            response = buildSuccessResponse(customerPICs);
        } else {
            response = buildBadRequestResponse("Project ID not found");
        }
        completed(methodName);
        return response;
    }
}
