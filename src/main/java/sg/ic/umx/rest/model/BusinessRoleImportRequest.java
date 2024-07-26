package sg.ic.umx.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class BusinessRoleImportRequest {

    @JsonProperty("data")
    private String data;

    public BusinessRoleImportRequest() {
        // Empty Constructor
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
