package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.ServiceLevelController;
import id.tsi.mmw.model.ServiceLevel;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.ServiceLevelRequest;
import id.tsi.mmw.rest.validator.ServiceLevelValidator;
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
@Path("serviceLevels")
@Produces(MediaType.APPLICATION_JSON)
public class ServiceLevelService extends BaseService {

    @Inject
    private ServiceLevelController serviceLevelController;

    private ServiceLevelValidator validator;

    public ServiceLevelService() {
        log = getLogger(this.getClass());
        validator = new ServiceLevelValidator();
    }

    @PermitAll
    @GET
    public Response getServiceLevelList() {
        final String methodName = "getServiceLevelList";
        start(methodName);
        log.info(methodName, "Get service level list");

        List<ServiceLevel> ServiceLevels = serviceLevelController.getServiceLevelList();

        completed(methodName);
        return buildSuccessResponse(ServiceLevels);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getServiceLevel(@PathParam("uid") String uid) {
        final String methodName = "getServiceLevel";
        start(methodName);

        Response response;
        log.info(methodName, "Get service levels (" + uid + ")");

        boolean isExist = serviceLevelController.validateServiceLevel(uid);
        log.debug(methodName, "service level validation : " + isExist);

        if (isExist) {
            ServiceLevel ServiceLevel = serviceLevelController.getServiceLevel(uid);
            response = buildSuccessResponse(ServiceLevel);
        } else {
            response = buildBadRequestResponse("service level ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addServiceLevel(ServiceLevelRequest request) {
        final String methodName = "addServiceLevel";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            ServiceLevel ServiceLevel = new ServiceLevel();
            ServiceLevel.setDisplayName(request.getDisplayName());
            ServiceLevel.setDescription(request.getDescription());

            boolean inserted = serviceLevelController.addServiceLevel(ServiceLevel);
            if (inserted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("service level creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateServiceLevel(ServiceLevelRequest request) {
        final String methodName = "updateServiceLevel";
        start(methodName);

        Response response;
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);


        boolean isExist = serviceLevelController.validateServiceLevel(request.getUid());
        log.debug(methodName, "service level validation : " + isExist);

        if (isExist) {

            ServiceLevel ServiceLevel = new ServiceLevel();
            ServiceLevel.setUid(request.getUid());
            ServiceLevel.setDisplayName(request.getDisplayName());
            ServiceLevel.setDescription(request.getDescription());

            boolean updated = serviceLevelController.updateServiceLevel(ServiceLevel);
            log.debug(methodName, "service level update : " + updated);
            if (updated) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Failed update service level");
            }
        } else {
            response = buildBadRequestResponse("service level ID not found");
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteServiceLevel(@PathParam("uid") String uid) {
        final String methodName = "deleteDepartment";
        start(methodName);

        Response response;
        log.info(methodName, "Delete service level (" + uid + ")");

        boolean isExist = serviceLevelController.validateServiceLevel(uid);
        log.debug(methodName, "service level validation : " + isExist);

        if (isExist) {
            boolean deleted = serviceLevelController.deleteServiceLevel(uid);
            log.debug(methodName, "service level deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("service level deletion failed");
            }
        } else {
            response = buildBadRequestResponse("service level ID not found");
        }
        completed(methodName);
        return response;
    }

    @GET
    @Path("/export")
    public Response exportServiceLevel() {
        final String methodName = "exportServiceLevel";
        start(methodName);
        log.info(methodName, "Export service level");

        List<ServiceLevel> ServiceLevels = serviceLevelController.getServiceLevelList();

        String filename = "service-levels.csv";

        List<String> headerColumns = Arrays.asList("uid", "service level Name", "Description");

        List<CSVRecord> recordList = buildServiceLevelCSV(headerColumns, ServiceLevels);

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

    protected List<CSVRecord> buildServiceLevelCSV(List<String> headerList, List<ServiceLevel> ServiceLevels) {
        List<CSVRecord> recordList = new ArrayList<>();

        try {
            for (ServiceLevel ServiceLevel : ServiceLevels) {
                CSVRecord record = new CSVRecord();
                record.put(headerList.get(0), ServiceLevel.getUid());
                record.put(headerList.get(1), ServiceLevel.getDisplayName());
                record.put(headerList.get(2), ServiceLevel.getDescription());

                // Add the new CSV record to the list of records
                recordList.add(record);
            }
        } catch (Exception ex) {
            log.error("buildServiceLevelCSV", ex);
        }

        // Return the list of CSV records
        return recordList;
    }
}
