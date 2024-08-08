package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public class MasterSetting {
    @JsonProperty ("Uid")
    private String uid;
    @JsonProperty("SettingKey")
    private String settingKey;
    @JsonProperty("SettingValue")
    private String settingValue;
    @JsonProperty("Description")
    private String description;
    @JsonProperty("LastUpdated")
    private String lastUpdated;

    public String getUid(){
        return uid;
    }
    public void setUid(String uid){this.uid = uid;}
    public String getSettingKey(){
        return settingKey;
    }
    public void setSettingKey(String settingKey){this.settingKey = settingKey;}
    public String getSettingValue(){
        return settingValue;
    }
    public void setSettingValue(String settingValue){this.settingValue = settingValue;}
    public String getDescription(){
        return description;
    }
    public void setDescription(String description){this.description = description;}
    public String getLastUpdated(){
        return lastUpdated;
    }
    public void setLastUpdated(String lastUpdated){this.lastUpdated = lastUpdated;}
}
