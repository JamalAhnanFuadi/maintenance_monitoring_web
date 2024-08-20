package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public class CustomerMaintenance {
    @JsonProperty ("maintenanceId")
    private String maintenanceId;
    @JsonProperty ("equipmentId")
    private String equipmentId;
    @JsonProperty("maintenanceType")
    private String maintenanceType;
    @JsonProperty("maintenanceDate")
    private String maintenanceDate;
    @JsonProperty("description")
    private String description;
    @JsonProperty("performedBy")
    private String performedBy;
    @JsonProperty("createDt")
    private String createDt;
    @JsonProperty("modifyDt")
    private String modifyDt;


    public String getMaintenanceId(){return maintenanceId;}
    public void setMaintenanceId(String maintenanceId){this.maintenanceId = maintenanceId;}
    public String getEquipmentId(){return equipmentId;}
    public void setEquipmentId(String equipmentId){this.equipmentId = equipmentId;}
    public String getMaintenanceType(){return maintenanceType;}
    public void setMaintenanceType(String maintenanceType){this.maintenanceType = maintenanceType;}
    public String getMaintenanceDate(){return maintenanceDate;}
    public void setMaintenanceDate(String maintenanceDate){this.maintenanceDate = maintenanceDate;}
    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}
    public String getPerformedBy(){return performedBy;}
    public void setPerformedBy(String performedBy){this.performedBy = performedBy;}
    public String getCreateDt(){return createDt;}
    public void setCreateDt(String createDt){this.createDt = createDt;}
    public String getModifyDt(){return modifyDt;}
    public void setModifyDt(String modifyDt){this.modifyDt = modifyDt;}

}
