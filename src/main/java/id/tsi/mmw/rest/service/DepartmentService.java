package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.DepartmentController;
import id.tsi.mmw.model.Application;
import id.tsi.mmw.model.Department;
import id.tsi.mmw.model.User;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.DepartmentRequest;
import id.tsi.mmw.rest.model.request.UserRequest;
import id.tsi.mmw.rest.validator.DepartmentValidator;
import id.tsi.mmw.rest.validator.UserValidator;
import id.tsi.mmw.util.helper.DateHelper;
import id.tsi.mmw.util.json.JsonHelper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Singleton
@Path("departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentService extends BaseService{

    @Inject
    private DepartmentController departmentController;

    private DepartmentValidator validator;

    public DepartmentService() {
        log = getLogger(this.getClass());
        validator = new DepartmentValidator();
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
    public Response getDepartmentList() {
        final String methodName = "getDepartmentList";
        start(methodName);
        log.info(methodName, "Get Department List");

        List<Department> departments = departmentController.getDepartmentList();

        completed(methodName);
        return buildSuccessResponse(departments);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getDepartment(@PathParam("uid") String uid) {
        final String methodName = "getDepartment";
        start(methodName);

        Response response;
        log.info(methodName, "Get department (" + uid + ")");

        boolean isExist = departmentController.validateDepartment(uid);
        log.debug(methodName, "Department validation : " + isExist);

        if (isExist) {
            Department department = departmentController.getDepartment(uid);
            response = buildSuccessResponse(department);
        } else {
            response = buildBadRequestResponse("Department ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addDepartment(DepartmentRequest request) {
        final String methodName = "addDepartment";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            Department department = new Department();
            department.setDisplayName(request.getDisplayName());
            department.setDescription(request.getDescription());

            boolean inserted = departmentController.addDepartment(department);
            if(inserted) {
                response = buildSuccessResponse();
            }else {
                response = buildBadRequestResponse("Department creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateDepartment(DepartmentRequest request) {
        final String methodName = "updateDepartment";
        start(methodName);

        Response response;
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);


        boolean isExist = departmentController.validateDepartment(request.getUid());
        log.debug(methodName, "Department validation : " + isExist);

        if (isExist) {

            Department department = new Department();
            department.setUid(request.getUid());
            department.setDisplayName(request.getDisplayName());
            department.setDescription(request.getDescription());

            boolean deleted = departmentController.updateDepartment(department);
            log.debug(methodName, "Department update : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Failed update department");
            }
        } else {
            response = buildBadRequestResponse("Department ID not found");
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteDepartment(@PathParam("uid") String uid) {
        final String methodName = "deleteDepartment";
        start(methodName);

        Response response;
        log.info(methodName, "Delete department (" + uid + ")");

        boolean isExist = departmentController.validateDepartment(uid);
        log.debug(methodName, "Department validation : " + isExist);

        if (isExist) {
            boolean deleted = departmentController.delete(uid);
            log.debug(methodName, "User deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Department deletion failed");
            }
        } else {
            response = buildBadRequestResponse("Department ID not found");
        }
        completed(methodName);
        return response;
    }
}
