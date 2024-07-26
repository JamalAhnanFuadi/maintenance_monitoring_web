package sg.ic.umx.rest.model;

import javax.ws.rs.core.Response.Status;
import sg.ic.umx.model.Execution;

public class ExecutionResponse extends ServiceResponse {

    private Execution execution;

    public ExecutionResponse() {
        super(Status.OK);
    }

    public Execution getExecution() {
        return execution;
    }

    public void setExecution(Execution execution) {
        this.execution = execution;
    }

}
