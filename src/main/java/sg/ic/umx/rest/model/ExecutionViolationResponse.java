package sg.ic.umx.rest.model;

import java.util.List;
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.umx.engine.model.RuleViolation;

public class ExecutionViolationResponse extends ServiceResponse {

    @JsonProperty("violations")
    private List<RuleViolation> violationList;

    public ExecutionViolationResponse() {
        super(Status.OK);
    }

    public List<RuleViolation> getViolationList() {
        return violationList;
    }

    public void setViolationList(List<RuleViolation> violationList) {
        this.violationList = violationList;
    }


}
