package sg.ic.umx.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.umx.model.Execution;

public class ApplicationViolationResponse extends ExecutionViolationResponse {

    @JsonProperty("execution")
    private Execution execution;

    public ApplicationViolationResponse() {
        super();
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }
}
