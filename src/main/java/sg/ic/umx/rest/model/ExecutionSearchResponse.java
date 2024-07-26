package sg.ic.umx.rest.model;

import java.util.List;
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.umx.model.Execution;

public class ExecutionSearchResponse extends ServiceResponse {

    @JsonProperty("executions")
    private List<Execution> executionList;

    public ExecutionSearchResponse() {
        super(Status.OK);
    }

    public List<Execution> getExecutionList() {
        return executionList;
    }

    public void setExecutionList(List<Execution> executionList) {
        this.executionList = executionList;
    }


}
