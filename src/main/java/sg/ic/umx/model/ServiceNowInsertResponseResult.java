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

public class ServiceNowInsertResponseResult {
    @JsonProperty("row")
    private String row;

    @JsonProperty("detail")
    private ServiceNowInsertResponseDetail detail;

    public ServiceNowInsertResponseResult() {
        // empty constructor
    }

    public String getRow() {
        return row;
    }

    public void setRow(String row) {
        this.row = row;
    }

    public ServiceNowInsertResponseDetail getDetail() {
        return detail;
    }

    public void setDetail(ServiceNowInsertResponseDetail detail) {
        this.detail = detail;
    }
}
