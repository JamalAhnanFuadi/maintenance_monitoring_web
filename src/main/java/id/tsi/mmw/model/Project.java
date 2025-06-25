package id.tsi.mmw.model;

import java.util.List;

public class Project {

    private String uid;
    private String displayName;
    private String salesOrderNumber;
    private String jobCode;
    private String description;
    private boolean status;
    private String createDt;
    private String modifyDt;

    private String customerUid;
    private String customerName;

    private List<ProjectTag> projectTags;
    private List<ProjectStaffPIC> staffPic;
    private List<ProjectCustomerPIC> customerPic;

    public Project() {
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

    public String getSalesOrderNumber() {
        return salesOrderNumber;
    }

    public void setSalesOrderNumber(String salesOrderNumber) {
        this.salesOrderNumber = salesOrderNumber;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
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

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public List<ProjectTag> getProjectTags() {
        return projectTags;
    }

    public void setProjectTags(List<ProjectTag> projectTags) {
        this.projectTags = projectTags;
    }

    public List<ProjectStaffPIC> getStaffPic() {
        return staffPic;
    }

    public void setStaffPic(List<ProjectStaffPIC> staffPic) {
        this.staffPic = staffPic;
    }

    public List<ProjectCustomerPIC> getCustomerPic() {
        return customerPic;
    }

    public void setCustomerPic(List<ProjectCustomerPIC> customerPic) {
        this.customerPic = customerPic;
    }
}
