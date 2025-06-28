package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.ProjectController;
import id.tsi.mmw.controller.ProjectHistoryController;
import id.tsi.mmw.model.*;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.ProjectRequest;
import id.tsi.mmw.rest.validator.ProjectValidator;
import id.tsi.mmw.util.helper.DateHelper;
import id.tsi.mmw.util.json.JsonHelper;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Singleton
@Path("projects")
@Produces(MediaType.APPLICATION_JSON)
public class ProjectService extends BaseService {

    @Inject
    private ProjectController projectController;

    @Inject
    private ProjectHistoryController projectHistoryController;

    private ProjectValidator validator;

    public ProjectService() {
        log = getLogger(this.getClass());
        validator = new ProjectValidator();
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

    @GET
    @Path("/history/{uid}")
    @PermitAll
    public Response getProjectHistory(@PathParam("uid") String uid) {
        final String methodName = "getProjectHistory";
        start(methodName);

        Response response;
        log.info(methodName, "Get Project History (" + uid + ")");

        boolean isExist = projectController.validateProject(uid);
        log.debug(methodName, "Project validation : " + isExist);

        if (isExist) {
            List<ProjectHistory> getProjectHistory = projectHistoryController.getProjectHistory(uid);
            response = buildSuccessResponse(getProjectHistory);
        } else {
            response = buildBadRequestResponse("Project ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addProject(ProjectRequest request) {
        final String methodName = "addProject";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            LocalDateTime now = LocalDateTime.now();
            String createdDate = DateHelper.formatDBDateTime(now);

            Project project = new Project();
            String projectUid = UUID.randomUUID().toString();
            project.setUid(projectUid);
            project.setDisplayName(request.getProjectName());
            project.setCustomerUid(request.getCustomerUid());
            project.setSalesOrderNumber(request.getSoNumber());
            project.setJobCode(request.getJobCode());
            project.setDescription(request.getDescription());
            project.setCreateDt(createdDate);

            List<ProjectStaffPIC> projectStaffList= new ArrayList<>();
            for(String staffPic : request.getStaffPic()) {
                ProjectStaffPIC projectStaffPIC = new ProjectStaffPIC();
                projectStaffPIC.setProjectUid(projectUid);
                projectStaffPIC.setStaffUid(staffPic);
                projectStaffPIC.setCreateDt(createdDate);
                projectStaffList.add(projectStaffPIC);

            }
            project.setStaffPic(projectStaffList);

            boolean inserted = projectController.addProject(project);
            if (inserted) {
                Staff staff = getSessionAttribute(Constants.SESSION_USER, Staff.class);
                String staffName = staff.getFirstname() + " " + staff.getLastname();

                ProjectHistory projectHistory = new ProjectHistory();
                projectHistory.setProjectUid(projectUid);
                projectHistory.setActor(staffName);
                projectHistory.setEvent(Constants.EVENT_CREATED);
                projectHistory.setMessageTitle("Project Created");
                projectHistory.setMessageDetail(project.getDisplayName() +" has been created");
                projectHistory.setIcon(Constants.ICON_PROJECT_CREATED);
                projectHistory.setDate(DateHelper.formatDate(now));
                projectHistory.setTime(DateHelper.formatTime(now));
                projectHistory.setCreateDt(createdDate);
                projectHistory.setRequest(JsonHelper.toJson(project));

                projectHistoryController.addProjectHistory(projectHistory);

                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Product creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response markProjectForDeletion(@PathParam("uid") String uid) {
        final String methodName = "markProjectForDeletion";
        start(methodName);

        Response response;
        log.info(methodName, "Mark project (" + uid + ") for deletion");

        boolean isExist = projectController.validateProject(uid);
        log.debug(methodName, "Project validation : " + isExist);

        if (isExist) {

            boolean deleted = projectController.markForDeletion(uid);
            log.debug(methodName, "Mark project deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Mark project deletion failed");
            }
        } else {
            response = buildBadRequestResponse("Project ID not found");
        }
        completed(methodName);
        return response;
    }
}
