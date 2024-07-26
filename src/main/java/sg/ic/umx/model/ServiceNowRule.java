/**
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS HEADER.
 *
 * Copyright (c) 2019 Identiticoders, and individual contributors as indicated by the @author tags. All Rights Reserved
 *
 * The contents of this file are subject to the terms of the Common Development and Distribution License (the License).
 *
 * Everyone is permitted to copy and distribute verbatim copies of this license document, but changing it is not
 * allowed.
 *
 */
package sg.ic.umx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ServiceNowRule {

    @JsonIgnore
    private Long id;

    @JsonProperty("u_active")
    private String active;
    @JsonProperty("u_action")
    private String action;

    @JsonProperty("u_application_name")
    private String applicationName;
    @JsonProperty("u_batch_flag")
    private String batchFlag;
    @JsonProperty("u_company_name")
    private String companyName;
    @JsonProperty("u_cotile_code")
    private String cotileCode;
    @JsonProperty("u_department_id")
    private String departmentId;
    @JsonProperty("u_department_short_name")
    private String departmentShortName;
    @JsonProperty("u_description")
    private String description;
    @JsonProperty("u_ic_license")
    private String icLicense;
    @JsonProperty("u_jobcode")
    private String jobCode;

    @JsonProperty("u_license_insurance")
    private String licenseInsurance;
    @JsonProperty("u_role_description")
    private String roleDescription;
    @JsonProperty("u_role_name")
    private String roleName;
    @JsonProperty("u_number")
    private String number;

    @JsonIgnore
    private String hash;

    public ServiceNowRule() {
        // Empty Constructor
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public void setApplicationName(String applicationName) {
        this.applicationName = applicationName;
    }

    public String getBatchFlag() {
        return batchFlag;
    }

    public void setBatchFlag(String batchFlag) {
        this.batchFlag = batchFlag;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public String getCotileCode() {
        return cotileCode;
    }

    public void setCotileCode(String cotileCode) {
        this.cotileCode = cotileCode;
    }

    public String getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(String departmentId) {
        this.departmentId = departmentId;
    }

    public String getDepartmentShortName() {
        return departmentShortName;
    }

    public void setDepartmentShortName(String departmentShortName) {
        this.departmentShortName = departmentShortName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getIcLicense() {
        return icLicense;
    }

    public void setIcLicense(String icLicense) {
        this.icLicense = icLicense;
    }

    public String getJobCode() {
        return jobCode;
    }

    public void setJobCode(String jobCode) {
        this.jobCode = jobCode;
    }

    public String getLicenseInsurance() {
        return licenseInsurance;
    }

    public void setLicenseInsurance(String licenseInsurance) {
        this.licenseInsurance = licenseInsurance;
    }

    public String getRoleDescription() {
        return roleDescription;
    }

    public void setRoleDescription(String roleDescription) {
        this.roleDescription = roleDescription;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }
}
