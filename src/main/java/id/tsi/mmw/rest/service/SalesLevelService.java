package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.SalesLevelController;
import id.tsi.mmw.model.SalesLevel;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.rest.model.request.SalesLevelRequest;
import id.tsi.mmw.rest.validator.SalesLevelValidator;
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
@Path("salesLevels")
@Produces(MediaType.APPLICATION_JSON)
public class SalesLevelService extends BaseService {

    @Inject
    private SalesLevelController salesLevelController;

    private SalesLevelValidator validator;

    public SalesLevelService() {
        log = getLogger(this.getClass());
        validator = new SalesLevelValidator();
    }

    @PermitAll
    @GET
    public Response getSalesLevelList() {
        final String methodName = "getSalesLevelList";
        start(methodName);
        log.info(methodName, "Get sales level list");

        List<SalesLevel> salesLevels = salesLevelController.getSalesLevelList();

        completed(methodName);
        return buildSuccessResponse(salesLevels);
    }

    @GET
    @Path("{uid}")
    @PermitAll
    public Response getSalesLevel(@PathParam("uid") String uid) {
        final String methodName = "getSalesLevel";
        start(methodName);

        Response response;
        log.info(methodName, "Get sales levels (" + uid + ")");

        boolean isExist = salesLevelController.validateSalesLevel(uid);
        log.debug(methodName, "Sales Level validation : " + isExist);

        if (isExist) {
            SalesLevel salesLevel = salesLevelController.getSalesLevel(uid);
            response = buildSuccessResponse(salesLevel);
        } else {
            response = buildBadRequestResponse("Sales Level ID not found");
        }
        completed(methodName);
        return response;
    }

    @POST
    @PermitAll
    public Response addSalesLevel(SalesLevelRequest request) {
        final String methodName = "addSalesLevel";
        Response response = null;
        start(methodName);
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.create(request);
        log.debug(methodName, "Request payload validation : " + validPayload);

        if (validPayload) {
            SalesLevel salesLevel = new SalesLevel();
            salesLevel.setDisplayName(request.getDisplayName());
            salesLevel.setDescription(request.getDescription());

            boolean inserted = salesLevelController.addSalesLevel(salesLevel);
            if (inserted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Sales Level creation failed");
            }
        } else {
            response = buildBadRequestResponse(Constants.MESSAGE_INVALID_REQUEST);
        }
        completed(methodName);
        return response;
    }

    @PUT
    @PermitAll
    public Response updateSalesLevel(SalesLevelRequest request) {
        final String methodName = "updateSalesLevel";
        start(methodName);

        Response response;
        log.info(methodName, JsonHelper.toJson(request));

        boolean validPayload = validator.update(request);
        log.debug(methodName, "Request payload validation : " + validPayload);


        boolean isExist = salesLevelController.validateSalesLevel(request.getUid());
        log.debug(methodName, "Sales Level validation : " + isExist);

        if (isExist) {

            SalesLevel salesLevel = new SalesLevel();
            salesLevel.setUid(request.getUid());
            salesLevel.setDisplayName(request.getDisplayName());
            salesLevel.setDescription(request.getDescription());

            boolean updated = salesLevelController.updateSalesLevel(salesLevel);
            log.debug(methodName, "Sales Level update : " + updated);
            if (updated) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Failed update sales level");
            }
        } else {
            response = buildBadRequestResponse("Sales Level ID not found");
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{uid}")
    @PermitAll
    public Response deleteSalesLevel(@PathParam("uid") String uid) {
        final String methodName = "deleteDepartment";
        start(methodName);

        Response response;
        log.info(methodName, "Delete Sales Level (" + uid + ")");

        boolean isExist = salesLevelController.validateSalesLevel(uid);
        log.debug(methodName, "Sales Level validation : " + isExist);

        if (isExist) {
            boolean deleted = salesLevelController.deleteSalesLevel(uid);
            log.debug(methodName, "Sales Level deletion : " + deleted);
            if (deleted) {
                response = buildSuccessResponse();
            } else {
                response = buildBadRequestResponse("Sales Level deletion failed");
            }
        } else {
            response = buildBadRequestResponse("Sales Level ID not found");
        }
        completed(methodName);
        return response;
    }

    @GET
    @Path("/export")
    public Response exportSalesLevel() {
        final String methodName = "exportSalesLevel";
        start(methodName);
        log.info(methodName, "Export sales level");

        List<SalesLevel> salesLevels = salesLevelController.getSalesLevelList();

        String filename = "sales-levels.csv";

        List<String> headerColumns = Arrays.asList("uid", "Sales Level Name", "Description");

        List<CSVRecord> recordList = buildSalesLevelCSV(headerColumns, salesLevels);

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

    protected List<CSVRecord> buildSalesLevelCSV(List<String> headerList, List<SalesLevel> salesLevels) {
        List<CSVRecord> recordList = new ArrayList<>();

        try {
            for (SalesLevel salesLevel : salesLevels) {
                CSVRecord record = new CSVRecord();
                record.put(headerList.get(0), salesLevel.getUid());
                record.put(headerList.get(1), salesLevel.getDisplayName());
                record.put(headerList.get(2), salesLevel.getDescription());

                // Add the new CSV record to the list of records
                recordList.add(record);
            }
        } catch (Exception ex) {
            log.error("buildSalesLevelCSV", ex);
        }

        // Return the list of CSV records
        return recordList;
    }
}
