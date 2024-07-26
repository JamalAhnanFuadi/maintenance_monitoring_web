package sg.ic.umx.rest.service;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.codec.Charsets;
import sg.ic.umx.controller.ApplicationController;
import sg.ic.umx.controller.ExecutionController;
import sg.ic.umx.controller.ExecutionDataController;
import sg.ic.umx.controller.RuleViolationController;
import sg.ic.umx.engine.ExecutionEngine;
import sg.ic.umx.engine.model.ExecutionData;
import sg.ic.umx.engine.model.RuleViolation;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Execution;
import sg.ic.umx.model.ExecutionStatus;
import sg.ic.umx.rest.model.ExecutionListResponse;
import sg.ic.umx.rest.model.ExecutionResponse;
import sg.ic.umx.rest.model.ExecutionSearchRequest;
import sg.ic.umx.rest.model.ExecutionSearchResponse;
import sg.ic.umx.rest.model.ExecutionViolationResponse;
import sg.ic.umx.util.csv.CSVRecord;
import sg.ic.umx.util.csv.CSVWriter;
import sg.ic.umx.util.helper.DateHelper;
import sg.ic.umx.util.helper.StringHelper;

@Singleton
@Path("executions")
@Produces(MediaType.APPLICATION_JSON)
public class ExecutionService extends BaseService {

    private final Executor executor;

    private final ExecutionController executionController;

    private final RuleViolationController violationController;

    private final ExecutionDataController dataController;

    private final ApplicationController applicationController;


    public ExecutionService() {
        log = getLogger(this.getClass());
        executor = initThreadPool();
        executionController = new ExecutionController();
        dataController = new ExecutionDataController();
        violationController = new RuleViolationController();
        applicationController = new ApplicationController();
    }

    @POST
    @PermitAll
    @Consumes(MediaType.APPLICATION_JSON)
    public Response listByApplicationId(ExecutionSearchRequest searchRequest) {

        List<Execution> result = null;
        if (StringHelper.validate(searchRequest.getStartDate()) && StringHelper.validate(searchRequest.getEndDate())) {

            // Start of start date
            LocalDateTime begin = ZonedDateTime.parse(searchRequest.getStartDate())
                    .withZoneSameInstant(ZoneId.systemDefault()).toLocalDate().atStartOfDay();

            // Start of end date + 1
            LocalDateTime end = ZonedDateTime.parse(searchRequest.getEndDate())
                    .withZoneSameInstant(ZoneId.systemDefault()).toLocalDate().plusDays(1).atStartOfDay();

            result = executionController.listByApplicationIdAndDate(searchRequest.getApplicationId(), begin, end);
        } else {
            result = executionController.listByApplicationId(searchRequest.getApplicationId());
        }

        ExecutionSearchResponse response = new ExecutionSearchResponse();
        response.setExecutionList(result);

        return Response.ok(response).build();
    }

    @GET
    @PermitAll
    @Path("{executionId}")
    public Response get(@PathParam("executionId") String executionId) {
        Response response = null;

        Execution execution = executionController.get(executionId);

        if (execution != null) {
            ExecutionResponse executionResponse = new ExecutionResponse();
            ExecutionData stat = dataController.getByExecutionId(executionId);
            execution.setStatistic(stat);
            executionResponse.setExecution(execution);
            response = buildSuccessResponse(executionResponse);
        } else {
            response = buildResourceNotFoundResponse();
        }
        return response;
    }

    @GET
    @PermitAll
    @Path("{executionId}/violations")
    public Response getViolations(@PathParam("executionId") String executionId) {

        ExecutionViolationResponse response = new ExecutionViolationResponse();
        response.setViolationList(violationController.listByExecutionId(executionId));

        return Response.ok(response).build();
    }

    @GET
    @Path("{executionId}/violations/export")
    public Response getViolationsDownload(@PathParam("executionId") String executionId) {

        final String methodName = "export";
        start(methodName);

        Execution execution = executionController.get(executionId);

        if (execution != null) {
            Application application = applicationController.get(execution.getApplicationId());

            String fileDateTime = DateHelper.formatFileDateTime(execution.getStartDt());

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
        } else {
            return buildInvalidRequestResponse();
        }
    }

    @DELETE
    @PermitAll
    @Path("{executionId}")
    public Response delete(@PathParam("executionId") String executionId) {

        dataController.deleteByExecutionId(executionId);
        violationController.deleteByExecutionId(executionId);
        boolean result = executionController.delete(executionId);

        return result ? buildSuccessResponse() : getErrorResponse();
    }

    @POST
    @PermitAll
    @Path("execute/{applicationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response execute(@PathParam("applicationId") final long applicationId) {
        Response response = null;

        // Check if application Exists
        Application application = applicationController.get(applicationId);

        if (application != null) {
            // Check if any existing executions of application
            List<Execution> runningExecutionList =
                    executionController.listByApplicationId(applicationId, ExecutionStatus.STARTED);

            if (runningExecutionList.isEmpty()) {
                ExecutionEngine engine = new ExecutionEngine(applicationId);
                executor.execute(engine::compute);
                response = buildSuccessResponse();
            } else {
                response = getAcceptedResponse("An execution is already running");
            }

        } else {
            response = buildResourceNotFoundResponse();
        }
        return response;
    }

    @GET
    @PermitAll
    @Path("applications/{applicationId}/active")
    public Response getActiveExecutions(@PathParam("applicationId") final long applicationId) {
        final String methodName = "getActiveExecutions";
        start(methodName);
        List<Execution> runningExecutionList =
                executionController.listByApplicationId(applicationId, ExecutionStatus.STARTED);
        ExecutionListResponse response = new ExecutionListResponse();
        response.setExecutionList(runningExecutionList);
        completed(methodName);
        return buildSuccessResponse(response);
    }

    @GET
    @PermitAll
    @Path("applications/{applicationId}/violations")
    public Response getApplicationViolations(@PathParam("applicationId") final long applicationId) {
        final String methodName = "getApplicationViolations";
        start(methodName);
        // Get Latest Execution
        Execution execution =
                executionController.getLatestExecutionByApplicationId(applicationId, ExecutionStatus.COMPLETED);

        ExecutionViolationResponse response = new ExecutionViolationResponse();
        response.setViolationList(violationController.listByExecutionId(execution.getId()));

        completed(methodName);
        return buildSuccessResponse(response);
    }
}
