package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserAccessMatrix {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("application")
    private String application;
    @JsonProperty("applicationCode")
    private String applicationCode;
    @JsonProperty("accessLevel")
    private String accessLevel;
    @JsonProperty("grantBy")
    private String grantBy;
    @JsonProperty("expiredDt")
    private String expiredDt;
    @JsonProperty("modifyDt")
    private String modifyDt;
    @JsonProperty("createDt")
    private String createDt;

    public UserAccessMatrix() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public void setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
    }

    public String getAccessLevel() {
        return accessLevel;
    }

    public void setAccessLevel(String accessLevel) {
        this.accessLevel = accessLevel;
    }

    public String getGrantBy() {
        return grantBy;
    }

    public void setGrantBy(String grantBy) {
        this.grantBy = grantBy;
    }

    public String getExpiredDt() {
        return expiredDt;
    }

    public void setExpiredDt(String expiredDt) {
        this.expiredDt = expiredDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }

    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }
}
