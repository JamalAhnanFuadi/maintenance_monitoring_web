package id.tsi.mmw.rest.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class UserRequest {

    @JsonProperty("uid")
    private String uid;

    @JsonProperty("firstname")
    private String firstname;

    @JsonProperty("lastname")
    private String lastname;

    @JsonProperty("email")
    private String email;

    @JsonProperty("mobileNumber")
    private String mobileNumber;

    @JsonProperty("departmentUid")
    private String departmentUid;

    @JsonProperty("dob")
    private String dob;

    @JsonProperty("status")
    private boolean status;

    public UserRequest() {
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

    public String getDepartmentUid() {
        return departmentUid;
    }

    public void setDepartmentUid(String departmentUid) {
        this.departmentUid = departmentUid;
    }

    public String getDob() {
        return dob;
    }

    public void setDob(String dob) {
        this.dob = dob;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
