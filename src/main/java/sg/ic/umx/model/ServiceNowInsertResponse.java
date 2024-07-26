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

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

public class ServiceNowInsertResponse {
    @JsonProperty("result")
    private List<ServiceNowInsertResponseResult> resultList;

    public ServiceNowInsertResponse() {
        this.resultList = new ArrayList<>();
    }

    public List<ServiceNowInsertResponseResult> getResultList() {
        return resultList;
    }

    public void setResultList(List<ServiceNowInsertResponseResult> resultList) {
        this.resultList = resultList;
    }
}
