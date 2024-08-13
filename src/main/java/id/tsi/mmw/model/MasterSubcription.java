package id.tsi.mmw.model;
import com.fasterxml.jackson.annotation.JsonProperty;
public class MasterSubcription {

    @JsonProperty ("subId")
    private String subId;
    @JsonProperty ("subName")
    private String subName;
    @JsonProperty ("subDescription")
    private String subDescription;
    @JsonProperty("subPrice")
    private String subPrice;
    @JsonProperty("subDuration")
    private String subDuration;
    @JsonProperty("subType")
    private String subType;
    @JsonProperty ("createDt")
    private String createDt;
    @JsonProperty ("modifyDt")
    private String modifyDt;

    public String getSubId(){return subId;}
    public void setSubId(String subId){this.subId = subId;}
    public String getSubName(){return subName;}
    public void setSubName(String subName){this.subName = subName;}
    public String getSubDescription(){return subDescription;}
    public void setSubDescription(String subDescription){this.subDescription = subDescription;}
    public String getSubPrice(){return subPrice;}
    public void setSubPrice(String subPrice){this.subPrice = subPrice;}
    public String getSubDuration(){return subDuration;}
    public void setSubDuration(String subDuration){this.subDuration = subDuration;}
    public String getSubType(){return subType;}
    public void setSubType(String subType){this.subType = subType;}
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
