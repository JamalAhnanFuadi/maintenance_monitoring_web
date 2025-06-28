package id.tsi.mmw.rest.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ProjectRequest {

    @JsonProperty("uid")
    private String uid;
    @JsonProperty("projectName")
    private String projectName;
    @JsonProperty("customerUid")
    private String customerUid;
    @JsonProperty("soNumber")
    private String soNumber;
    @JsonProperty("jobCode")
    private String jobCode;
    @JsonProperty("description")
    private String description;
    @JsonProperty("staffPic")
    private List<String> staffPic;

    public ProjectRequest() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    public String getCustomerUid() {
        return customerUid;
    }

    public void setCustomerUid(String customerUid) {
        this.customerUid = customerUid;
    }

    public String getSoNumber() {
        return soNumber;
    }

    public void setSoNumber(String soNumber) {
        this.soNumber = soNumber;
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

    public List<String> getStaffPic() {
        return staffPic;
    }

    public void setStaffPic(List<String> staffPic) {
        this.staffPic = staffPic;
    }
}
