package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.DepartmentController;
import id.tsi.mmw.model.Department;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.DepartmentRequest;
import id.tsi.mmw.rest.validator.DepartmentValidator;
import id.tsi.mmw.util.csv.CSVRecord;
import id.tsi.mmw.util.csv.CSVWriter;
import id.tsi.mmw.util.json.JsonHelper;
import org.apache.commons.codec.Charsets;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Singleton
@Path("departments")
@Produces(MediaType.APPLICATION_JSON)
public class DepartmentService extends BaseService {

    @Inject
    private DepartmentController departmentController;

    private DepartmentValidator validator;

    public DepartmentService() {
        log = getLogger(this.getClass());
        validator = new DepartmentValidator();
    }

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
            if (inserted) {
                response = buildSuccessResponse();
            } else {
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

    @GET
    @Path("/export")
    public Response exportDepartment() {
        final String methodName = "exportDepartment";
        start(methodName);
        log.info(methodName, "Export department");

        List<Department> departmentList = departmentController.getDepartmentList();

        String filename = "department.csv";

        List<String> headerColumns = Arrays.asList("uid", "Department Name", "Description");

        List<CSVRecord> recordList = buildDepartmentCSV(headerColumns, departmentList);

        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream output) throws IOException {
                try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(output, Charsets.UTF_8))) {
                    // Write the report to the output stream
                    CSVWriter.getInstance().write(pw, headerColumns, recordList);
                }
            }
        };

        completed(methodName);
        return Response.ok(stream, "text/csv").header("content-disposition", "attachment;filename=" + filename).build();
    }

    protected List<CSVRecord> buildDepartmentCSV(List<String> headerList, List<Department> departmentList) {
        List<CSVRecord> recordList = new ArrayList<>();

        try {
            for (Department department : departmentList) {
                CSVRecord record = new CSVRecord();
                record.put(headerList.get(0), department.getUid());
                record.put(headerList.get(1), department.getDisplayName());
                record.put(headerList.get(2), department.getDescription());

                // Add the new CSV record to the list of records
                recordList.add(record);
            }
        } catch (Exception ex) {
            log.error("buildDepartmentCSV", ex);
        }

        // Return the list of CSV records
        return recordList;
    }
}
