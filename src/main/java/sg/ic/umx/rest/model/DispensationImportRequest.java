package sg.ic.umx.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class DispensationImportRequest {

    @JsonProperty("applicationId")
    private long applicationId;

    @JsonProperty("data")
    private String data;

    public DispensationImportRequest() {
        // Empty Constructor
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }
}
