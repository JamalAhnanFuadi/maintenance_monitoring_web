package sg.ic.umx.engine;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import sg.ic.umx.controller.ApplicationController;
import sg.ic.umx.controller.DispensationController;
import sg.ic.umx.controller.EmailController;
import sg.ic.umx.controller.ExecutionController;
import sg.ic.umx.controller.ExecutionDataController;
import sg.ic.umx.controller.IDGController;
import sg.ic.umx.controller.RuleController;
import sg.ic.umx.controller.RuleViolationController;
import sg.ic.umx.controller.ServerController;
import sg.ic.umx.controller.SettingController;
import sg.ic.umx.engine.model.AccountType;
import sg.ic.umx.engine.model.ApplicationAccount;
import sg.ic.umx.engine.model.CombinedRule;
import sg.ic.umx.engine.model.ExecutionData;
import sg.ic.umx.engine.model.RuleViolation;
import sg.ic.umx.engine.model.ViolationResult;
import sg.ic.umx.idg.model.IDGUser;
import sg.ic.umx.idg.model.ItemPair;
import sg.ic.umx.idg.model.RoleListResponse;
import sg.ic.umx.idg.model.UserListResponse;
import sg.ic.umx.manager.CacheManager;
import sg.ic.umx.manager.EncryptionManager;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Dispensation;
import sg.ic.umx.model.Execution;
import sg.ic.umx.model.ExecutionStatus;
import sg.ic.umx.model.Rule;
import sg.ic.umx.model.Server;
import sg.ic.umx.model.Setting;
import sg.ic.umx.util.constant.ExecutionEngineConstant;
import sg.ic.umx.util.constant.SettingName;
import sg.ic.umx.util.helper.DateHelper;
import sg.ic.umx.util.json.JsonHelper;
import sg.ic.umx.util.log.AppLogger;

public class ExecutionEngine {

    private final AppLogger log;
    private final SettingController settingController;
    private final ApplicationController applicationController;

    private final IDGController idgController;

    private final ExecutionController executionController;
    private final RuleViolationController violationController;
    private final ExecutionDataController executionDataController;
    private final EmailController emailController;
    private final ServerController serverController;

    private final long applicationId;
    private final String executionId;
    private Pattern pattern;
    private Set<String> whitelistSet;

    private long whitelistAccounts = 0;
    private long whitelistCompliant = 0;
    private long whitelistNonCompliant = 0;

    private long normalAccounts = 0;
    private long normalCompliant = 0;
    private long normalNonCompliant = 0;

    private Map<String, String> settingMap;

    private static final String APP_NAME_TEMPLATE = "{AppName}";

    public ExecutionEngine(long applicationId) {
        log = new AppLogger(this.getClass());
        this.applicationId = applicationId;
        this.executionId = UUID.randomUUID().toString();

        // Controllers
        settingController = new SettingController();
        applicationController = new ApplicationController();
        executionController = new ExecutionController();
        violationController = new RuleViolationController();
        executionDataController = new ExecutionDataController();
        serverController = new ServerController();

        List<Setting> settingList = settingController.list();

        settingMap = new HashMap<>();

        settingList.forEach(setting -> settingMap.put(setting.getName(), setting.getValue()));

        idgController = new IDGController();

        emailController = new EmailController();
        emailController.setHost(settingMap.get(SettingName.MAIL_SERVER_HOST));
        emailController.setSender(settingMap.get(SettingName.MAIL_SENDER));
        emailController.setPort(Integer.parseInt(settingMap.get(SettingName.MAIL_SERVER_PORT)));
    }

    public void compute() {
        final String methodName = "compute";

        // OUTPUT
        List<RuleViolation> violationList;
        ExecutionData stat = new ExecutionData(executionId);
        Application application = applicationController.get(applicationId);

        // Set Server
        Optional<Server> server = serverController.get(application.getServer().getId());

        if (server.isPresent()) {
            application.setServer(server.get());
        }

        String reportName =
                ExecutionEngineConstant.EXECUTION_NAME_TEMPLATE.replace(APP_NAME_TEMPLATE, application.getName())
                        .replace("{DateTime}", DateHelper.formatDateTime(LocalDateTime.now()));

        startExecution(reportName);
        String rolePattern = ExecutionEngineConstant.ROLE_REGEX_TEMPLATE.replace(APP_NAME_TEMPLATE,
                escapeRegex(application.getName()));

        pattern = Pattern.compile(rolePattern);

        try {
            log(methodName, "Retrieve Whitelist Accounts");
            // White List Account Set
            whitelistSet = getWhiteListAccountSet();

            log(methodName, "Retrieve Rule List");
            List<Rule> ruleList = getRuleList();
            stat.setRulesProcessed(ruleList.size());
            Map<String, CombinedRule> combinedRuleMap = getCombinedRuleMap(ruleList);

            log(methodName, "Retrieve Rule Attribute List");
            // Rule Attribute List
            List<String> ruleAttributeList = getRuleAttributeList();

            log(methodName, "Retrieve Users");
            // Users
            Map<String, String> userHashMap = getUserHashMap(application, ruleAttributeList);
            log(methodName, "Retrieve Accounts and Roles");
            // Accounts' Roles
            Map<String, ApplicationAccount> accountRoleMap = getAccountRoleMap(application);

            stat.setAccountsProcessed(accountRoleMap.size());

            log(methodName, "Start Processing");
            violationList = executeCompute(accountRoleMap, userHashMap, combinedRuleMap);
            log(methodName, "Processing Complete");

            stat.setWhitelistProcessed(whitelistAccounts);
            stat.setWhitelistCompliant(whitelistCompliant);
            stat.setWhitelistNonCompliant(whitelistNonCompliant);

            stat.setNormalProcessed(normalAccounts);
            stat.setNormalCompliant(normalCompliant);
            stat.setNormalNonCompliant(normalNonCompliant);

            saveExecutionData(stat);
            saveExecutionViolation(violationList);
            completeExecution();
            sendEmailNotification(application);
        } catch (Exception ex) {
            // Update Execution to Failed Status
            failedExecution();
            log.error(executionId, methodName, ex);
        }
        log(methodName, "Execution Complete");
    }

    public List<RuleViolation> executeCompute(Map<String, ApplicationAccount> accountRoleMap,
            Map<String, String> userHashMap, Map<String, CombinedRule> combinedRuleMap) {
        // Account Map Iteration
        List<RuleViolation> violationList = new ArrayList<>();

        for (Map.Entry<String, ApplicationAccount> entry : accountRoleMap.entrySet()) {

            ApplicationAccount applicationAccount = entry.getValue();

            ViolationResult result = new ViolationResult();

            String userHash = userHashMap.get(applicationAccount.getUserId());

            if (combinedRuleMap.containsKey(userHash)) { // User Match a Rule
                CombinedRule combinedRule = combinedRuleMap.get(userHash);

                for (String userRole : applicationAccount.getRoleSet()) {

                    // Check for violation
                    if (!combinedRule.getRoleSet().contains(userRole)) {
                        result.addRole(userRole);
                    }
                }

            } else { // User does not match any rules, all roles are a violation
                result.addRole(applicationAccount.getRoleSet());
            }

            Optional<RuleViolation> ruleViolation = processViolationResult(entry, result);

            if (ruleViolation.isPresent()) {
                violationList.add(ruleViolation.get());
            }

        } // End of Account Map Iteration

        return violationList;
    }

    private Optional<RuleViolation> processViolationResult(Map.Entry<String, ApplicationAccount> entry,
            ViolationResult violationResult) {

        Optional<RuleViolation> result = Optional.empty();

        RuleViolation ruleViolation = new RuleViolation(executionId);

        if (whitelistSet.contains(entry.getKey())) { // Whitelist Account
            whitelistAccounts++;
            if (violationResult.isHasViolated()) {
                whitelistNonCompliant++;
                ruleViolation.setAccountType(AccountType.WHITELIST);
            } else {
                whitelistCompliant++;
            }

        } else { // Normal Account
            normalAccounts++;
            if (violationResult.isHasViolated()) {
                normalNonCompliant++;
                ruleViolation.setAccountType(AccountType.NORMAL);

            } else {
                normalCompliant++;
            }

            // Populate Rule Violation Object
            if (violationResult.isHasViolated()) {
                ruleViolation.setAccountId(entry.getKey());
                ruleViolation.setUserId(entry.getValue().getUserId());
                ruleViolation.setRoleList(violationResult.getRoleViolationList());
                result = Optional.of(ruleViolation);
            }
        }
        return result;
    }

    private void saveExecutionViolation(List<RuleViolation> violationList) {
        violationController.create(violationList);
    }

    private void saveExecutionData(ExecutionData stat) {
        executionDataController.create(stat);
    }

    private Set<String> getWhiteListAccountSet() {
        List<Dispensation> dispensationList = new DispensationController().listByApplicationId(applicationId);
        return dispensationList.stream().map(Dispensation::getName).collect(Collectors.toSet());
    }

    private List<String> getRuleAttributeList() {
        return applicationController.get(applicationId).getAttributeList();
    }

    private List<Rule> getRuleList() {
        return new RuleController().listByApplicationId(applicationId);
    }

    private Map<String, CombinedRule> getCombinedRuleMap(List<Rule> ruleList) {
        Map<String, CombinedRule> ruleMap = new HashMap<>();
        ruleList.forEach(rule -> {

            if (ruleMap.containsKey(rule.getHash())) {
                ruleMap.get(rule.getHash()).addRole(rule.getRoleName());

            } else {
                CombinedRule combinedRule = new CombinedRule();
                combinedRule.setAttributeMap(rule.getAttributeMap());
                combinedRule.addRole(rule.getRoleName());

                ruleMap.put(rule.getHash(), combinedRule);
            }
        });
        return ruleMap;
    }

    private Map<String, ApplicationAccount> getAccountRoleMap(Application application) {
        final String methodName = "getAccountRoleMap";
        Map<String, ApplicationAccount> accountMap = new HashMap<>();

        if (CacheManager.getInstance().isAccountRoleValid(application.getId())) {
            log(methodName, "Cache is valid");
            accountMap = CacheManager.getInstance().getAccountRoleMap(application.getId());
            log(methodName, "Retrieving info from cache");
        } else {
            log(methodName, "Retrieving info from IDG");
            RoleListResponse roleListResponse =
                    idgController.getRoles(application.getServer(), application.getConfigurationName());

            if (!roleListResponse.getRoleList().isEmpty()) {
                log(methodName, "Successful Retrieval of info From IDG");

                accountMap = processRoleList(roleListResponse.getRoleList());

                CacheManager.getInstance().addAccountRoleMap(application.getId(), accountMap);
            } else {
                log(methodName, "Role List is Empty");
            }
        }
        return accountMap;

    }

    private Map<String, ApplicationAccount> processRoleList(List<ItemPair> roleList) {
        final String methodName = "processRoleList";
        Map<String, ApplicationAccount> accountMap = new HashMap<>();
        for (ItemPair itemPair : roleList) {
            if (itemPair.getLeft().getDataType().equals("USER") && itemPair.getRight().getDataType().equals("ROLE")) {

                String userId = itemPair.getLeft().getNames().get(0);
                String accountRole = itemPair.getRight().getNames().get(0);

                Matcher matcher = pattern.matcher(accountRole);

                // Role matches template
                if (matcher.find()) {
                    // 1 - Account Name
                    String accountId = matcher.group(1);

                    // 2 - Role Name
                    String role = matcher.group(2);

                    if (accountMap.containsKey(accountId)) {
                        ApplicationAccount account = accountMap.get(accountId);
                        account.addRole(role);
                    } else {
                        ApplicationAccount account = new ApplicationAccount();
                        account.setUserId(userId);
                        account.setAccountId(accountId);
                        account.addRole(role);
                        accountMap.put(accountId, account);
                    }

                } else {
                    log(methodName, "No Match for : " + userId + " : " + accountRole);
                    // No Match
                }
            }
        }
        return accountMap;
    }

    private Map<String, String> getUserHashMap(Application application, List<String> ruleAttributeList) {
        final String methodName = "getUserHashMap";
        Map<String, String> userHashMap = new HashMap<>();

        if (CacheManager.getInstance().isUserHashValid(application.getId())) {
            log(methodName, "Cache is Valid");
            userHashMap = CacheManager.getInstance().getUserHashMap(application.getId());
            log(methodName, "Retrieve info from cache");
        } else {
            log(methodName, "Retrieve info from IDG");
            UserListResponse userListResponse =
                    idgController.getUsers(application.getServer(), application.getConfigurationName());

            if (!userListResponse.getUserList().isEmpty()) {

                log(methodName, "Successful Retrieval of info from IDG");

                for (IDGUser user : userListResponse.getUserList()) {
                    String userName = user.getName().getName();
                    SortedMap<String, String> userMap = getUserAttributeMap(user, ruleAttributeList);
                    String hash = EncryptionManager.hash(JsonHelper.toJson(userMap));
                    userHashMap.put(userName, hash);
                }
                CacheManager.getInstance().addUserHashMap(application.getId(), userHashMap);
            } else {
                log(methodName, "User Info is empty");
            }
        }
        return userHashMap;
    }

    private SortedMap<String, String> getUserAttributeMap(IDGUser user, List<String> attributeList) {
        SortedMap<String, String> userAttributeMap = new TreeMap<>();
        attributeList.forEach(
                attribute -> userAttributeMap.put(attribute, user.getAttributeMap().getOrDefault(attribute, "")));
        return userAttributeMap;
    }

    public String getExecutionId() {
        return executionId;
    }

    private void startExecution(String reportName) {
        Execution execution = new Execution(executionId);
        execution.setApplicationId(applicationId);
        execution.setName(reportName);
        execution.setStatus(ExecutionStatus.STARTED);
        execution.setStartDt(LocalDateTime.now());
        executionController.create(execution);
    }

    private void completeExecution() {
        Execution execution = new Execution(executionId);
        execution.setStatus(ExecutionStatus.COMPLETED);
        execution.setCompletedDt(LocalDateTime.now());
        executionController.update(execution);
    }

    private void failedExecution() {
        Execution execution = new Execution(executionId);
        execution.setStatus(ExecutionStatus.FAILED);
        execution.setCompletedDt(LocalDateTime.now());
        executionController.update(execution);
    }

    private void sendEmailNotification(Application application) {
        String fileUrl = settingMap.get(SettingName.APPLICATION_URL) + "/system/executions/" + executionId
                + "/violations/export";

        String content = application.getMailBody() + "\n" + fileUrl;

        boolean result = emailController.send(application.getRecipientList(), application.getMailSubject(), content);

        if (result) {
            log("sendEmailNotification", "Email Sending Successful");
        } else {
            log("sendEmailNotification", "Email Sending Failed");
        }
    }

    private void log(String methodName, String message) {
        log.debug(executionId, "[" + methodName + "] " + message);
    }

    private String escapeRegex(String regex) {
        String output = "";

        output = regex.replace("[", "\\[");
        output = output.replace("]", "\\]");
        output = output.replace("(", "\\(");
        output = output.replace(")", "\\)");

        return output;

    }
}
