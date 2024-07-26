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
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import org.apache.commons.codec.Charsets;
import sg.ic.umx.controller.ApplicationController;
import sg.ic.umx.controller.RuleController;
import sg.ic.umx.manager.EncryptionManager;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Rule;
import sg.ic.umx.rest.model.RuleImportRequest;
import sg.ic.umx.rest.model.RuleListResponse;
import sg.ic.umx.util.csv.CSVReader;
import sg.ic.umx.util.csv.CSVRecord;
import sg.ic.umx.util.csv.CSVWriter;
import sg.ic.umx.util.helper.DateHelper;
import sg.ic.umx.util.json.JsonHelper;

@Singleton
@PermitAll
@Path("rules")
@Produces(MediaType.APPLICATION_JSON)
public class RuleService extends BaseService {

    private final ApplicationController applicationController;
    private final RuleController ruleController;
    private static final String PROP_ROLE_NAME = "Role Name";

    public RuleService() {
        log = getLogger(this.getClass());
        applicationController = new ApplicationController();
        ruleController = new RuleController();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response add(Rule rule) {

        // Generate Hash
        String hash = EncryptionManager.hash(JsonHelper.toJson(rule.getAttributeMap()));
        rule.setHash(hash);

        boolean result = ruleController.create(rule);

        return result ? buildSuccessResponse() : getErrorResponse();
    }

    @GET
    public RuleListResponse list(@QueryParam("applicationId") long applicationId) {

        RuleListResponse result = new RuleListResponse();
        Application application = applicationController.get(applicationId);

        if (application != null) {
            result.setRuleList(ruleController.listByApplicationId(applicationId));
            result.setFieldList(application.getAttributeList());
        }

        return result;
    }

    @POST
    @Path("delete")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(Rule rule) {
        boolean result = ruleController.delete(rule.getId());
        return result ? buildSuccessResponse() : getErrorResponse();
    }

    @POST
    @Path("import")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doImport(RuleImportRequest req) {
        String data = req.getData();

        List<CSVRecord> recordList = null;

        // Parse CSV Input
        if (data != null) {
            recordList = CSVReader.getInstance().read(data);
        } else {
            return buildInvalidRequestResponse();
        }

        if (!recordList.isEmpty()) {
            // Clear Current List of Rules
            ruleController.deleteByApplicationId(req.getApplicationId());

            // Process CSV
            List<Rule> ruleList = convertCSVToRule(req.getApplicationId(), recordList);

            // Create Rules
            ruleController.create(req.getApplicationId(), ruleList);

            Set<String> headerSet = recordList.get(0).getHeaders();
            headerSet.remove(PROP_ROLE_NAME);

            // Update Endpoint Attribute Set
            Application application = applicationController.get(req.getApplicationId());
            application.setAttributeList(new ArrayList<>(headerSet));
            applicationController.update(application);

            return buildSuccessResponse();
        } else {
            return buildInvalidRequestResponse();
        }
    }

    @GET
    @Path("export")
    public Response doExport(@QueryParam("applicationId") long applicationId) {

        final String methodName = "export";
        start(methodName);

        final String fileDateTime = DateHelper.formatFileDateTime(LocalDateTime.now());

        // Retrieve Application Info
        Application application = applicationController.get(applicationId);

        final String fileName = "Rule List - " + application.getName() + " " + fileDateTime + ".csv";

        // Retrieve Rule List
        List<Rule> ruleList = ruleController.listByApplicationId(applicationId);

        List<CSVRecord> recordList = convertRuleToCSV(ruleList);

        // Retrieve Column List
        List<String> headerList = new ArrayList<>(application.getAttributeList());
        headerList.add(PROP_ROLE_NAME);

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
            return Response.ok(stream, "text/csv").header("content-disposition", "attachment;filename=" + fileName)
                    .build();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        return getErrorResponse();
    }

    private List<Rule> convertCSVToRule(long applicationId, List<CSVRecord> recordList) {
        List<Rule> ruleList = new ArrayList<>();
        Set<String> attributeSet = new HashSet<>();

        attributeSet.addAll(recordList.get(0).getHeaders());
        attributeSet.remove(PROP_ROLE_NAME);

        recordList.forEach(record -> {

            Rule rule = new Rule();
            rule.setApplicationId(applicationId);

            // Role Name
            rule.setRoleName(record.get(PROP_ROLE_NAME));

            // Attributes
            SortedMap<String, String> attributeMap = new TreeMap<>();
            attributeSet.forEach(key -> attributeMap.put(key, record.get(key)));
            rule.setAttributeMap(attributeMap);

            // Hash
            String hash = EncryptionManager.hash(JsonHelper.toJson(attributeMap));
            rule.setHash(hash);

            ruleList.add(rule);
        });
        return ruleList;
    }

    private List<CSVRecord> convertRuleToCSV(List<Rule> ruleList) {
        List<CSVRecord> recordList = new ArrayList<>();
        ruleList.forEach(rule -> {
            CSVRecord record = new CSVRecord();
            record.put(PROP_ROLE_NAME, rule.getRoleName());
            rule.getAttributeMap().forEach(record::put);
            recordList.add(record);
        });
        return recordList;
    }
}
