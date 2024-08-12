package id.tsi.mmw.model;
import com.fasterxml.jackson.annotation.JsonProperty;
public class MasterService {
    @JsonProperty ("serviceId")
    private String serviceId;
    @JsonProperty ("serviceName")
    private String serviceName;
    @JsonProperty ("serviceDescription")
    private String serviceDescription;
    @JsonProperty ("servicePrice")
    private String servicePrice;
    @JsonProperty ("serviceStatus")
    private String serviceStatus;
    @JsonProperty ("createDt")
    private String createDt;
    @JsonProperty ("modifyDt")
    private String modifyDt;

    public String getServiceId(){return serviceId;}
    public void setServiceId(String serviceId){this.serviceId = serviceId;}
    public String getServiceName(){return serviceName;}
    public void setServiceName(String serviceName){this.serviceName = serviceName;}
    public String getServiceDescription(){return serviceDescription;}
    public void setServiceDescription(String serviceDescription){this.serviceDescription = serviceDescription;}
    public String getServicePrice(){return servicePrice;}
    public void setServicePrice(String servicePrice){this.servicePrice = servicePrice;}
    public String getServiceStatus(){return serviceStatus;}
    public void setServiceStatus(String serviceStatus){this.serviceStatus = serviceStatus;}
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
