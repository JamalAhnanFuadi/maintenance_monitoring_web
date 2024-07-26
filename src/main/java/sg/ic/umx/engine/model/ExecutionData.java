package sg.ic.umx.engine.model;

public class ExecutionData {

    private String executionId;
    private long accountsProcessed;
    private long rulesProcessed;
    private long whitelistProcessed;
    private long whitelistCompliant;
    private long whitelistNonCompliant;
    private long normalProcessed;
    private long normalCompliant;
    private long normalNonCompliant;

    public ExecutionData() {
        // Empty Constructor
    }

    public ExecutionData(String executionId) {
        this.executionId = executionId;
    }

    public String getExecutionId() {
        return executionId;
    }

    public void setExecutionId(String executionId) {
        this.executionId = executionId;
    }

    public long getAccountsProcessed() {
        return accountsProcessed;
    }

    public void setAccountsProcessed(long accountsProcessed) {
        this.accountsProcessed = accountsProcessed;
    }

    public long getRulesProcessed() {
        return rulesProcessed;
    }

    public void setRulesProcessed(long rulesProcessed) {
        this.rulesProcessed = rulesProcessed;
    }

    public long getWhitelistCompliant() {
        return whitelistCompliant;
    }

    public void setWhitelistCompliant(long whitelistCompliant) {
        this.whitelistCompliant = whitelistCompliant;
    }

    public long getWhitelistNonCompliant() {
        return whitelistNonCompliant;
    }

    public void setWhitelistNonCompliant(long whitelistNonCompliant) {
        this.whitelistNonCompliant = whitelistNonCompliant;
    }

    public long getNormalCompliant() {
        return normalCompliant;
    }

    public void setNormalCompliant(long normalCompliant) {
        this.normalCompliant = normalCompliant;
    }

    public long getNormalNonCompliant() {
        return normalNonCompliant;
    }

    public void setNormalNonCompliant(long normalNonCompliant) {
        this.normalNonCompliant = normalNonCompliant;
    }

    public long getWhitelistProcessed() {
        return whitelistProcessed;
    }

    public void setWhitelistProcessed(long whitelistProcessed) {
        this.whitelistProcessed = whitelistProcessed;
    }

    public long getNormalProcessed() {
        return normalProcessed;
    }

    public void setNormalProcessed(long normalProcessed) {
        this.normalProcessed = normalProcessed;
    }


}
