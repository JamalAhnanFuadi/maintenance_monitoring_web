package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MasterUserGroup {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("groupKey")
    private String groupKey;

    @JsonProperty("description")
    private String description;

    @JsonProperty("createDt")
    private String createDt;

    @JsonProperty("modifyDt")
    private String modifyDt;

    public String getUid() {
        return uid;
    }

    public void setUid() {
        this.uid = uid;
    }

    public String getGroupKey(){return groupKey;}
    public void setGroupKey(String groupKey){this.groupKey = groupKey;}

    public String getDescription(){return description;}
    public void setdescription (String description){this.description = description;}

    public String getCreateDt() {
        return createDt;
    }
    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getModifyDt() {
        return modifyDt;
    }
    public void setModifyDt(String modifyDt) {
        this.modifyDt = modifyDt;
    }

}
