package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class Staff {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("department")
    private String department;

    @JsonProperty("departmentUid")
    private String departmentUid;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("photoUrl")
    private String photoUrl;

    @JsonProperty("status")
    private boolean status;

    @JsonProperty("createDt")
    private String createDt;

    @JsonProperty("modifyDt")
    private String modifyDt;

    public Staff() {
        // default constructor
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getDepartmentUid() {
        return departmentUid;
    }

    public void setDepartmentUid(String departmentUid) {
        this.departmentUid = departmentUid;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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
}
