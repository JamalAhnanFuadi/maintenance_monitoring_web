package id.tsi.mmw.model;

public class Product {

    private String uid;
    private String displayName;
    private String principalUid;
    private String principalName;
    private String categoryUid;
    private String categoryName;
    private String brandUid;
    private String brandName;
    private String description;
    private Boolean active;
    private String createDt;
    private String modifyDt;
    private boolean locked;

    public Product() {
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

    public String getPrincipalUid() {
        return principalUid;
    }

    public void setPrincipalUid(String principalUid) {
        this.principalUid = principalUid;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public void setPrincipalName(String principalName) {
        this.principalName = principalName;
    }

    public String getCategoryUid() {
        return categoryUid;
    }

    public void setCategoryUid(String categoryUid) {
        this.categoryUid = categoryUid;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getBrandUid() {
        return brandUid;
    }

    public void setBrandUid(String brandUid) {
        this.brandUid = brandUid;
    }

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

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

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}
