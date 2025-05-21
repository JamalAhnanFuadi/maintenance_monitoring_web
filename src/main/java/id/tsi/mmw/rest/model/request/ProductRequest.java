package id.tsi.mmw.rest.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ProductRequest {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("displayName")
    private String displayName;
    @JsonProperty("categoryUid")
    private String categoryUid;
    @JsonProperty("brandUid")
    private String brandUid;
    @JsonProperty("description")
    private String description;
    @JsonProperty("active")
    private boolean active;

    public ProductRequest() {
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

    public String getCategoryUid() {
        return categoryUid;
    }

    public void setCategoryUid(String categoryUid) {
        this.categoryUid = categoryUid;
    }

    public String getBrandUid() {
        return brandUid;
    }

    public void setBrandUid(String brandUid) {
        this.brandUid = brandUid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}
