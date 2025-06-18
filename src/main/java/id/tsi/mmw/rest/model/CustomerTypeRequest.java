package id.tsi.mmw.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CustomerTypeRequest {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("description")
    private String description;

    public CustomerTypeRequest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
