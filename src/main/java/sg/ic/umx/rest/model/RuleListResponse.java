package sg.ic.umx.rest.model;

import java.util.ArrayList;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.umx.model.Rule;

public class RuleListResponse extends ServiceResponse {

    @JsonProperty("fields")
    private List<String> fieldList;

    @JsonProperty("rules")
    private List<Rule> ruleList;

    public RuleListResponse() {
        super(Status.OK);
        fieldList = new ArrayList<>();
        ruleList = new ArrayList<>();
    }

    public List<Rule> getRuleList() {
        return ruleList;
    }

    public void setRuleList(List<Rule> ruleList) {
        this.ruleList = ruleList;
    }

    public List<String> getFieldList() {
        return fieldList;
    }

    public void setFieldList(List<String> fieldList) {
        this.fieldList = fieldList;
    }


}
