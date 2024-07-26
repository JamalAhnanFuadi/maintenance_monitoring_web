package sg.ic.umx.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class RuleImportRequest {

    @JsonProperty("applicationId")
    private long applicationId;

    @JsonProperty("data")
    private String data;

    public RuleImportRequest() {
        // Empty Constructor
    }

    public long getApplicationId() {
        return applicationId;
    }

    public String getData() {
        return data;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public void setData(String data) {
        this.data = data;
    }
}
