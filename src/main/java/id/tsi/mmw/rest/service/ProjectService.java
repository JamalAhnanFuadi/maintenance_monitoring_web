package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.ProjectController;
import id.tsi.mmw.model.Project;
import id.tsi.mmw.model.ProjectTag;
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
        }

        List<Project> result = new ArrayList<>(projects);

        completed(methodName);
        return buildSuccessResponse(result);
    }
}
