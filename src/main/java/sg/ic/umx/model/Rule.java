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

import java.util.SortedMap;
import org.jdbi.v3.json.Json;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Rule {

    private long id;

    @JsonProperty("applicationId")
    private long applicationId;
    @JsonProperty("roleName")
    private String roleName;
    @JsonProperty("attributes")
    private SortedMap<String, String> attributeMap;
    @JsonProperty("hash")
    private String hash;

    @JsonProperty("serviceNowId")
    private String serviceNowId;

    @JsonProperty("action")
    private String action;

    public Rule() {
        // Empty Constructor
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(long applicationId) {
        this.applicationId = applicationId;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Json
    public SortedMap<String, String> getAttributeMap() {
        return attributeMap;
    }

    @Json
    public void setAttributeMap(SortedMap<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public String getHash() {
        return hash;
    }

    public void setHash(String hash) {
        this.hash = hash;
    }

    public String getServiceNowId() {
        return serviceNowId;
    }

    public void setServiceNowId(String serviceNowId) {
        this.serviceNowId = serviceNowId;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }
}
