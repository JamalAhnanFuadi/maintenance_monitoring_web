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
package sg.ic.umx.controller;

import org.apache.http.HttpHost;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.model.Rule;
import sg.ic.umx.model.ServiceNowInsertResponse;
import sg.ic.umx.model.ServiceNowRule;
import sg.ic.umx.util.http.HTTPClient;
import sg.ic.umx.util.http.model.HTTPRequest;
import sg.ic.umx.util.http.model.HTTPResponse;
import sg.ic.umx.util.json.JsonHelper;
import sg.ic.umx.util.property.Property;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class RuleController extends BaseController {

    private static final String APPLICATION_ID = "applicationId";

    private final ApplicationController applicationController;

    public RuleController() {
        log = getLogger(this.getClass());
        applicationController = new ApplicationController();
    }

    public boolean create(Rule rule) {
        final String methodName = "create";
        start(methodName);
        final String sql =
                "INSERT INTO [rule] (applicationId, roleName, attributeMap, hash) VALUES (:applicationId, :roleName, :attributeMap,:hash);";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(rule);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        // CR for Pushing to ServiceNow
        // Additional step to check if ApplicationID is the selected application to push to ServiceNow
        // If yes, push the rule information to the staging table for scheduled push to ServiceNow
        if (rule.getApplicationId() == applicationController.getApplicationIdByName(getProperty(Property.SERVICENOW_APPLICATION_PUSH))) {
            if (createToServiceNowStagingInsert(rule)) {
                log.debug(methodName, "Rule inserted to ServiceNow Staging table");
            } else {
                log.debug(methodName, "Rule failed in inserting to ServiceNow Staging table");
            }
        }
        // end CR

        completed(methodName);
        return result;
    }

    public boolean create(long applicationId, List<Rule> ruleList) {
        final String methodName = "create";
        start(methodName);
        final String sql =
                "INSERT INTO [rule] (applicationId, roleName, attributeMap, hash) VALUES (:applicationId, :roleName, :attributeMap,:hash);";
        boolean result = false;
        try (Handle h = getHandle()) {

            PreparedBatch batch = h.prepareBatch(sql);

            ruleList.forEach(rule -> batch.bindBean(rule).add());

            result = executeBatch(batch);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        // CR for Pushing to ServiceNow
        // Additional step to check if ApplicationID is the selected application to push to ServiceNow
        // If yes, push the rule information to the staging table for scheduled push to ServiceNow
        if (applicationId == applicationController.getApplicationIdByName(getProperty(Property.SERVICENOW_APPLICATION_PUSH))) {
            if (createToServiceNowStagingBulkInsert(ruleList)) {
                log.debug(methodName, "Rules inserted to ServiceNow Staging table");
            } else {
                log.debug(methodName, "Rules failed in inserting to ServiceNow Staging table");
            }
        }
        // end CR

        completed(methodName);
        return result;
    }

    public boolean delete(long id) {
        final String methodName = "delete";
        start(methodName);

        // CR for Pushing to ServiceNow
        // Additional step to check if ApplicationID is the selected application to push to ServiceNow
        // If yes, push the rule information to the staging table for scheduled push to ServiceNow
        final String snowSql = "SELECT applicationId FROM [rule] WHERE id = :id;";

        Integer applicationId = 0;
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(snowSql)) {
            applicationId = q.bind("id", id).mapTo(Integer.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        if (applicationId == applicationController.getApplicationIdByName(getProperty(Property.SERVICENOW_APPLICATION_PUSH))) {
            // get Rule and Insert into Staging table
            Rule rule = getRuleById(id);

            if (createToServiceNowStagingDelete(rule)) {
                log.debug(methodName, "Rule inserted to ServiceNow Staging table");
            } else {
                log.debug(methodName, "Rule failed in inserting to ServiceNow Staging table");
            }
        }
        // end CR

        final String sql = "DELETE FROM [rule] WHERE id = :id;";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("id", id);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean deleteByApplicationId(long applicationId) {
        final String methodName = "deleteByApplicationId";
        start(methodName);

        // CR for Pushing to ServiceNow
        // Additional step to check if ApplicationID is the selected application to push to ServiceNow
        // If yes, push the rule information to the staging table for scheduled push to ServiceNow
        if (applicationId == applicationController.getApplicationIdByName(getProperty(Property.SERVICENOW_APPLICATION_PUSH))) {

            // get all rule and Insert into Staging table
            List<Rule> ruleList = listByApplicationId(applicationId);

            if (createToServiceNowStagingBulkDelete(ruleList)) {
                log.debug(methodName, "Rules inserted to ServiceNow Staging table");
            } else {
                log.debug(methodName, "Rules failed in inserting to ServiceNow Staging table");
            }
        }
        // end CR

        final String sql = "DELETE FROM [rule] WHERE applicationId = :applicationId;";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind(APPLICATION_ID, applicationId);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<Rule> listByApplicationId(long applicationId) {
        final String methodName = "listByApplicationId";
        start(methodName);
        final String sql = "SELECT * FROM [rule] WHERE applicationId = :applicationId ORDER BY roleName,id;";
        List<Rule> result = new ArrayList<>();
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(APPLICATION_ID, applicationId).mapToBean(Rule.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public Rule get(Long applicationId) {
        final String methodName = "get";
        final String sql = "SELECT * FROM [rule] WHERE applicationId = :applicationId;";
        Optional<Rule> result = Optional.empty();
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(APPLICATION_ID, applicationId).mapToBean(Rule.class).findFirst();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result.isPresent() ? result.get() : null;
    }

    public Rule getRuleById(Long id) {
        final String methodName = "getRuleById";

        final String sql = "SELECT * FROM [rule] WHERE id = :id;";
        Rule result = new Rule();
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind("id", id).mapToBean(Rule.class).one();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        completed(methodName);
        return result;
    }

    public boolean createToServiceNowStagingInsert(Rule rule) {
        final String methodName = "createToServiceNowStagingInsert";
        start(methodName);
        final String sql =
                "INSERT INTO [rule_push_servicenow] (applicationId, roleName, attributeMap, hash, serviceNowId, action) VALUES (:applicationId, :roleName, :attributeMap, :hash, :serviceNowId, 'ADD');";

        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(rule);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean createToServiceNowStagingDelete(Rule rule) {
        final String methodName = "createToServiceNowStagingDelete";
        start(methodName);
        final String sql =
                "INSERT INTO [rule_push_servicenow] (applicationId, roleName, attributeMap, hash, serviceNowId, action) VALUES (:applicationId, :roleName, :attributeMap, :hash, :serviceNowId, 'DELETE');";

        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(rule);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean createToServiceNowStagingBulkInsert(List<Rule> ruleList) {
        final String methodName = "createToServiceNowStagingBulkInsert";
        start(methodName);
        final String sql =
                "INSERT INTO [rule_push_servicenow] (applicationId, roleName, attributeMap, hash, serviceNowId, action) VALUES (:applicationId, :roleName, :attributeMap, :hash, :serviceNowId, 'ADD');";
        boolean result = false;
        try (Handle h = getHandle()) {

            PreparedBatch batch = h.prepareBatch(sql);

            ruleList.forEach(rule -> batch.bindBean(rule).add());

            result = executeBatch(batch);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean createToServiceNowStagingBulkDelete(List<Rule> ruleList) {
        final String methodName = "createToServiceNowStagingBulkDelete";
        start(methodName);
        final String sql =
                "INSERT INTO [rule_push_servicenow] (applicationId, roleName, attributeMap, hash, serviceNowId, action) VALUES (:applicationId, :roleName, :attributeMap, :hash, :serviceNowId, 'DELETE');";

        boolean result = false;
        try (Handle h = getHandle()) {

            PreparedBatch batch = h.prepareBatch(sql);

            ruleList.forEach(rule -> batch.bindBean(rule).add());

            result = executeBatch(batch);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    // insert this new rule to ServiceNow
    public boolean insertToServiceNow(ServiceNowRule rule) {
        final String methodName = "insertToServiceNow";
        start(methodName);

        boolean result = false;

        List<ServiceNowRule> serviceNowRuleList = new ArrayList<>();

        serviceNowRuleList.add(rule);

        log.debug(methodName, "Request Payload: " + JsonHelper.toJsonUTF(serviceNowRuleList));

        HTTPRequest request =
                new HTTPRequest.Builder(getProperty(Property.SERVICENOW_INSERT_URL)).setBasicAuthentication(getProperty(Property.SERVICENOW_USERNAME), getProperty(Property.SERVICENOW_PASSWORD))
                        .setContentType("application/json;charset=UTF-8").build();

        HTTPResponse response;

        // check if Proxy is required or not, if TRUE, set the proxy parameters.
        if (getProperty(Property.UMX_PROXY_REQUIRED).equalsIgnoreCase("TRUE")) {
            // Proxy required to send to Service Now API from KBTG server.
            // Pull properties from here as not able to pull properties from static class
            HttpHost proxy = new HttpHost(getProperty(Property.UMX_PROXY_URL), Integer.parseInt(getProperty(Property.UMX_PROXY_PORT)));

            response = HTTPClient.postWithProxy(request, JsonHelper.toJsonUTF(serviceNowRuleList), proxy);
        } else {
            response = HTTPClient.post(request, JsonHelper.toJsonUTF(serviceNowRuleList));
        }

        log.debug(methodName, "Response Code: " + response.getCode());
        log.debug(methodName, "Response: " + response.getBody());

        // correct response should be something like the below
        // Response: {"result":[{"row":1.0,"detail":{"number":"UAMID0051685 insert successful."}}]}
        if (response.getBody().contains("result") && response.getBody().contains("insert successful")) {
            result = true;
            log.debug(methodName, "Extracting ServiceNowID from response");
            ServiceNowInsertResponse responseData = JsonHelper.fromJson(response.getBody(), ServiceNowInsertResponse.class);

            String numberString = responseData.getResultList().get(0).getDetail().getNumber();
            String[] numberArray = numberString.split(" ");
            String serviceNowId = numberArray[0];

            log.debug(methodName, "ServiceNow ID " + serviceNowId + " for Rule " + rule.getId());

            // set the serviceNowId back to Rule table based on ID
            if (updateServiceNowId(rule.getHash(), serviceNowId)) {
                log.debug(methodName, "ServiceNow ID " + serviceNowId + " updated back to rule hash " + rule.getHash() );
            } else {
                log.debug(methodName, "ServiceNow ID " + serviceNowId + " failed to update back to rule hash " + rule.getHash() );
            }
        } else {
            log.error(methodName, "Insert failed. Response: " + response.getBody());
        }

        completed(methodName);
        return result;
    }

    public boolean updateServiceNowId(String hash, String serviceNowId) {
        final String methodName = "updateServiceNowId";
        start(methodName);
        final String sql =
                "UPDATE [rule] set serviceNowId=:serviceNowId WHERE hash = :hash";

        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("serviceNowId", serviceNowId).bind("hash", hash);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    // deleted record remove from ServiceNow
    public boolean deleteFromServiceNow(ServiceNowRule rule) {
        final String methodName = "deleteFromServiceNow";
        start(methodName);

        boolean result = false;

        List<ServiceNowRule> serviceNowRuleList = new ArrayList<>();

        serviceNowRuleList.add(rule);

        log.debug(methodName, "Request Payload: " + JsonHelper.toJsonUTF(serviceNowRuleList));

        HTTPRequest request =
                new HTTPRequest.Builder(getProperty(Property.SERVICENOW_UPDATE_URL)).setBasicAuthentication(getProperty(Property.SERVICENOW_USERNAME), getProperty(Property.SERVICENOW_PASSWORD))
                        .setContentType("application/json").build();

        HTTPResponse response;

        // check if Proxy is required or not, if TRUE, set the proxy parameters.
        if (getProperty(Property.UMX_PROXY_REQUIRED).equalsIgnoreCase("TRUE")) {
            // Proxy required to send to Service Now API from KBTG server.
            // Pull properties from here as not able to pull properties from static class
            HttpHost proxy = new HttpHost(getProperty(Property.UMX_PROXY_URL), Integer.parseInt(getProperty(Property.UMX_PROXY_PORT)));

            response = HTTPClient.patchWithProxy(request, JsonHelper.toJsonUTF(serviceNowRuleList), proxy);
        } else {
            response = HTTPClient.patch(request, JsonHelper.toJsonUTF(serviceNowRuleList));
        }

        log.debug(methodName, "Response Code: " + response.getCode());
        log.debug(methodName, "Response: " + response.getBody());

        if (response.getBody().contains("result")) {
            result = true;
        }

        completed(methodName);
        return result;
    }

    public List<ServiceNowRule> convertToServiceNow(List<Rule> ruleList) {
        final String methodName = "convertToServiceNow";
        start(methodName);

        List<ServiceNowRule> list = new ArrayList<>();
        ServiceNowRule snRule = new ServiceNowRule();

        for (Rule rule : ruleList) {
            snRule.setId(rule.getId());
            snRule.setAction(rule.getAction());

            // setting the Active flag in the payload to be True if Add and False otherwise
            if (rule.getAction().equalsIgnoreCase("ADD")) {
                snRule.setActive("True");
            } else if (rule.getAction().equalsIgnoreCase("DELETE")) {
                snRule.setActive("False");
            }
            snRule.setApplicationName(handleUTFFormatting(rule.getAttributeMap().get("Application")));
            // empty unused field
            snRule.setBatchFlag("");
            // for Insert, the "number" should be empty as this is generated by ServiceNow
            snRule.setNumber("");
            snRule.setCompanyName(rule.getAttributeMap().get("Company Name"));
            snRule.setCotileCode(rule.getAttributeMap().get("Cotile Code"));
            snRule.setDepartmentId(rule.getAttributeMap().get("Department ID"));
            // empty unused field
            snRule.setDescription("");
            snRule.setIcLicense(rule.getAttributeMap().get("IC License"));
            snRule.setJobCode(rule.getAttributeMap().get("Jobcode"));
            snRule.setDepartmentShortName(rule.getAttributeMap().get("Department Short Name"));
            snRule.setLicenseInsurance(rule.getAttributeMap().get("License Insurance"));
            snRule.setRoleDescription(handleUTFFormatting(rule.getAttributeMap().get("Role Description")));
            snRule.setRoleName(handleUTFFormatting(rule.getRoleName()));

            // Hash is not sent to ServiceNow but used to identify the original rule
            snRule.setHash(rule.getHash());

            list.add(snRule);
        }

        completed(methodName);
        return list;
    }

    // scheduled job that is triggered by the scheduler
    public boolean pushServiceNow() {
        final String methodName = "pushServiceNow";
        start(methodName);

        int insertCount = 0;
        int deleteCount = 0;

        log.info(methodName, "Start scheduled task of pushing to ServiceNow...");

        log.debug(methodName, "Retrieving records from ServiceNow Staging table to push to ServiceNow");
        List<ServiceNowRule> listServiceNowRecords = listServiceNowStaging();

        log.debug(methodName, "Retrieved number of records: " + listServiceNowRecords.size());

        log.debug(methodName, "Processing changes in ascending time order");

        // for loop
        for (ServiceNowRule snRule : listServiceNowRecords) {
            if (snRule.getAction().equals("ADD")) {
                log.debug(methodName, "Inserting...");
                if (insertToServiceNow(snRule)) {
                    insertCount++;
                    deleteFromServiceNowStagingTable(snRule.getId());
                    log.debug(methodName, "Insert completed.");
                } else {
                    log.error(methodName, "Insert failed. ID: " + snRule.getId());
                }
            } else if (snRule.getAction().equals("DELETE")) {
                log.debug(methodName, "Deleting...");
                if (deleteFromServiceNow(snRule)) {
                    deleteCount++;
                    deleteFromServiceNowStagingTable(snRule.getId());
                    log.debug(methodName, "Delete completed.");
                } else {
                    log.error(methodName, "Delete failed. ID: " + snRule.getId());
                }
            } else {
                log.error(methodName, "Unknown change type, not INSERT or DELETE. Change Type: " + snRule.getAction());
            }
        }

        log.debug(methodName, "Delete records: " + deleteCount);
        log.debug(methodName, "Insert records: " + insertCount);

        completed(methodName);
        return true;
    }

    // get all pending tasks from the staging table
    public List<ServiceNowRule> listServiceNowStaging() {
        final String methodName = "listServiceNowStaging";
        start(methodName);
        final String sql = "SELECT * FROM [rule_push_servicenow] ORDER BY id;";
        List<Rule> result = new ArrayList<>();
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.mapToBean(Rule.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        completed(methodName);
        return convertToServiceNow(result);
    }

    private boolean deleteFromServiceNowStagingTable(long id) {
        final String methodName = "deleteFromServiceNowStagingTable";
        start(methodName);

        final String sql = "DELETE FROM [rule_push_servicenow] WHERE id = :id;";
        boolean result = false;
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("id", id);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    private String handleUTFFormatting(String string) {
        final String methodName = "handleUTFFormatting";
        start(methodName);

        String value = "";

        try {
            byte[] bytes = string.getBytes(StandardCharsets.UTF_8);

            value = new String(bytes, StandardCharsets.UTF_8);
        } catch (Exception e) {
            log.error("Exception", e);
        }

        completed(methodName);
        return value;
    }

}
