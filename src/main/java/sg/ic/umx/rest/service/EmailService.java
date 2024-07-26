/*
 * To change this license header, choose License Headers in Project Properties. To change this template file, choose
 * Tools | Templates and open the template in the editor.
 */
package sg.ic.umx.rest.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.codec.Charsets;
import sg.ic.umx.controller.ApplicationController;
import sg.ic.umx.controller.ExecutionController;
import sg.ic.umx.controller.RuleViolationController;
import sg.ic.umx.engine.model.RuleViolation;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Execution;
import sg.ic.umx.util.csv.CSVRecord;
import sg.ic.umx.util.csv.CSVWriter;
import sg.ic.umx.util.helper.DateHelper;

/**
 *
 * @author permadi
 */
@Path("report")
@Produces(MediaType.APPLICATION_JSON)
public class EmailService extends BaseService {

    private final ApplicationController applicationController;
    private final ExecutionController executionController;
    private final RuleViolationController violationController;

    public EmailService() {

        log = getLogger(this.getClass());
        applicationController = new ApplicationController();
        executionController = new ExecutionController();
        violationController = new RuleViolationController();

    }

    @GET
    @Path("/{executionId}")
    public Response downloadReport(@PathParam("executionId") String executionId) {

        final String methodName = "download";
        start(methodName);

        Execution execution = executionController.get(executionId);

        Application application = applicationController.get(execution.getApplicationId());

        String fileDateTime = DateHelper.formatFileDateTime(LocalDateTime.now());
        String filename = "Rule Violation List - " + application.getName() + " " + fileDateTime + ".csv";
        final List<String> headerList = Arrays.asList("userId", "accountId", "accountType", "role");

        List<RuleViolation> violationList = violationController.listByExecutionId(executionId);

        List<CSVRecord> recordList = convertRuleViolationToCSV(headerList, violationList);

        try {
            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream output) throws IOException {
                    try (PrintWriter pw = new PrintWriter(new OutputStreamWriter(output, Charsets.UTF_8))) {
                        CSVWriter.getInstance().write(pw, headerList, recordList);

                    } catch (Exception ex) {
                        log.error(methodName, ex);
                        throw ex;
                    }
                }
            };
            completed(methodName);
            return Response.ok(stream, "text/csv").header("content-disposition", "attachment;filename=" + filename)
                    .build();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        return getErrorResponse();
    }
}
