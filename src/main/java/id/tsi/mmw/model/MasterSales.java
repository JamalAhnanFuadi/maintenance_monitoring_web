package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class MasterSales {
    @JsonProperty("sale_id")
    private String saleId;
    @JsonProperty("sale_date")
    private String saleDate;
    @JsonProperty("customer_id")
    private String customerId;
    @JsonProperty("product_id")
    private String productId;
    @JsonProperty("sale_status")
    private String saleStatus;
    @JsonProperty ("create_dt")
    private String createDt;
    @JsonProperty ("modify_dt")
    private String modifyDt;

    public String getSaleId(){return saleId;}
    public void setSaleId(String saleId){this.saleId = saleId;}
    public String getSaleDate(){return saleDate;}
    public void setSaleDate(String saleDate){this.saleDate = saleDate;}
    public String getCustomerId(){return customerId;}
    public void setCustomerId(String customerId){this.customerId = customerId;}
    public String getProductId(){return productId;}
    public void setProductId(String productId){this.productId = productId;}
    public String getSaleStatus(){return saleStatus;}
    public void setSaleStatus(String saleStatus){this.saleStatus = saleStatus;}
    public String getCreateDt(){return createDt;}
    public void setCreateDt(String createDt){this.createDt = createDt;}
    public String getModifyDt(){return modifyDt;}
    public void setModifyDt(String modifyDt){this.modifyDt = modifyDt;}

}
