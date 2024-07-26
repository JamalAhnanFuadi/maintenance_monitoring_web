/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2019 Identiticoders, and individual contributors as indicated by the @author tags. All Rights Reserved
 *
 * The contents of this file are subject to the terms of the Common Development and Distribution License (the License).
 *
 * Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not
 * allowed.
 *
 */
package sg.ic.umx.rest.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.codec.Charsets;
import sg.ic.umx.controller.ApplicationController;
import sg.ic.umx.controller.DispensationController;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Dispensation;
import sg.ic.umx.rest.model.DispensationImportRequest;
import sg.ic.umx.rest.model.DispensationListResponse;
import sg.ic.umx.util.csv.CSVReader;
import sg.ic.umx.util.csv.CSVRecord;
import sg.ic.umx.util.helper.DateHelper;

@Singleton
@PermitAll
@Path("dispensations")
@Produces(MediaType.APPLICATION_JSON)
public class DispensationService extends BaseService {

    private final DispensationController dispensationController;
    private final ApplicationController applicationController;
    private static final String PROP_ACCOUNT_NAME = "Account Name";

    public DispensationService() {
        log = getLogger(this.getClass());
        dispensationController = new DispensationController();
        applicationController = new ApplicationController();
    }

    @GET
    public Response list(@QueryParam("applicationId") long applicationId) {

        DispensationListResponse response = new DispensationListResponse();

        response.setDispensationList(dispensationController.listByApplicationId(applicationId));

        return Response.ok(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Dispensation dispensation) {
        boolean result = dispensationController.create(dispensation);

        return result ? buildSuccessResponse() : getErrorResponse();
    }

    @DELETE
    @Path("{dispensationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("dispensationId") long dispensationId) {
        boolean result = dispensationController.delete(dispensationId);

        return result ? buildSuccessResponse() : getErrorResponse();
    }

    @POST
    @Path("import")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doImport(DispensationImportRequest req) {
        String data = req.getData();

        List<CSVRecord> recordList = null;

        if (data != null) {
            recordList = CSVReader.getInstance().read(data);
        } else {
            return buildInvalidRequestResponse();
        }

        if (!recordList.isEmpty()) {
            // Delete current accounts
            dispensationController.deleteByApplicationId(req.getApplicationId());

            List<Dispensation> accountList = new ArrayList<>();
            recordList.forEach(record -> {
                Dispensation dispensation = new Dispensation();
                dispensation.setApplicationId(req.getApplicationId());
                dispensation.setName(record.get(PROP_ACCOUNT_NAME));
                accountList.add(dispensation);
            });

            // Execute Query
            boolean result = dispensationController.create(accountList);

            return result ? buildSuccessResponse() : getErrorResponse();

        } else {
            return buildInvalidRequestResponse();
        }
    }

    @GET
    @Path("export")
    public Response export(@QueryParam("applicationId") long applicationId) {
        final String methodName = "export";
        start(methodName);

        // Retrieve Application Info
        Application application = applicationController.get(applicationId);

        String fileDateTime = DateHelper.formatFileDateTime(LocalDateTime.now());

        final String fileName = "Whitelist Accounts - " + application.getName() + " " + fileDateTime + ".csv";

        // Retrieve Account Info
        List<Dispensation> dispensationList = dispensationController.listByApplicationId(applicationId);

        try {
            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException {
                    try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(output, Charsets.UTF_8))) {

                        // Header
                        pw.println(PROP_ACCOUNT_NAME);

                        dispensationList.forEach(account -> pw.println(account.getName()));
                    } catch (Exception ex) {
                        log.error(methodName, ex);
                        throw ex;
                    }
                }
            };
            completed(methodName);
            return Response.ok(stream, "text/csv").header("content-disposition", "attachment;filename=" + fileName)
                    .build();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        return getErrorResponse();
    }
}
