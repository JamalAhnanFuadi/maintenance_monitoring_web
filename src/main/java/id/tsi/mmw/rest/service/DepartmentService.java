package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.DepartmentController;
import id.tsi.mmw.model.Application;
import id.tsi.mmw.model.Department;

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
@Path("departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentService extends BaseService{

    @Inject
    private DepartmentController departmentController;

    public DepartmentService() {
        log = getLogger(this.getClass());
    }

    /**
     * This method handles a GET request to the "departments" URL.
     * The method is marked as @PermitAll, meaning that no authentication
     * is required to access this endpoint.
     *
     * @return a JSON response containing a list of Department objects.
     */
    @PermitAll
    @GET
    public Response detDepartmentList() {
        final String methodName = "detDepartmentList";
        start(methodName);
        log.info(methodName, "Get Department List");

        // Call the getDepartmentList() method from the DepartmentController
        // class to retrieve the list of departments from the database.
        List<Department> departments = departmentController.getDepartmentList();

        // Call the completed() method to log the completion of the method.
        completed(methodName);

        // Return a JSON response containing the list of departments.
        // The buildSuccessResponse() method will create a Response object
        // with the appropriate HTTP status code (200) and include the
        // list of departments in the response body.
        return buildSuccessResponse(departments);
    }
}
