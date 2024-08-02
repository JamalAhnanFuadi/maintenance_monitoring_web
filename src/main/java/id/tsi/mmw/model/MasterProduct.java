package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MasterProduct {

    @JsonProperty("productId")
    private String productId;

    @JsonProperty ("name")
    private String name;

    @JsonProperty("description")
    private String description;

    @JsonProperty("createDt")
    private String createDt;

    @JsonProperty("modifyDt")
    private String modifyDt;

    public String getProductId(){return productId;}
    public void setProductId(String productId){this.productId = productId;}

    public String getName(){return name;}
    public void setName(String name){this.name = name;}

    public String getDescription(){return description;}
    public void setDescription(String description){this.description = description;}

    public String getCreateDt(){return createDt;}
    public void setCreateDt(String createDt){this.createDt = createDt;}

    public String getModifyDt(){return modifyDt;}
    public void setModifyDt(String modifyDt){this.modifyDt = modifyDt;}


}
