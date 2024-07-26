package sg.ic.umx.engine.model;

import java.util.ArrayList;
import java.util.List;
import org.jdbi.v3.json.Json;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleViolation {

    @JsonProperty("executionId")
    private String executionId;
    @JsonProperty("accountId")
    private String accountId;
    @JsonProperty("accountType")
    private AccountType accountType;
    @JsonProperty("userId")
    private String userId;
    @JsonProperty("roles")
    private List<String> roleList;

    public RuleViolation() {
        roleList = new ArrayList<>();
    }

    public RuleViolation(String executionId) {
        this();
        this.executionId = executionId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Json
    public List<String> getRoleList() {
        return roleList;
    }

    @Json
    public void setRoleList(List<String> roleList) {
        this.roleList = roleList;
    }

}
