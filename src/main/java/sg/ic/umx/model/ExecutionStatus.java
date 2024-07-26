package sg.ic.umx.model;

public enum ExecutionStatus {

    STARTED("STARTED"), COMPLETED("COMPLETED"), CANCELLED("CANCELLED"), FAILED("FAILED");

    private String status;

    private ExecutionStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    @Override
    public String toString() {
        return status;
    }

}
