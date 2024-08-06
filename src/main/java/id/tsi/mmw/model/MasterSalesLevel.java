package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MasterSalesLevel {
    @JsonProperty("uid")
    private String uid;

    @JsonProperty ("levelName")
    private String levelName;

    @JsonProperty("description")
    private String description;

    @JsonProperty("createDt")
    private String createDt;

    @JsonProperty("modifyDt")
    private String modifyDt;

    public String getuid(){return uid;}
    public void setuid(String uid){this.uid = uid;}

    public String getName(){return levelName;}
    public void setName(String name){this.levelName = levelName;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public String getCreateDt(){return createDt;}
    public void setCreateDt(String createDt){this.createDt = createDt;}

    public String getModifyDt(){return modifyDt;}
    public void setModifyDt(String modifyDt){this.modifyDt = modifyDt;}


}
