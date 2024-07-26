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

import java.util.List;
import org.jdbi.v3.json.Json;
import com.fasterxml.jackson.annotation.JsonProperty;

public class Application {

    @JsonProperty("id")
    private long id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("configurationName")
    private String configurationName;
    @JsonProperty("recipients")
    private List<String> recipientList;
    @JsonProperty("attributes")
    private List<String> attributeList;
    @JsonProperty("mailSubject")
    private String mailSubject;
    @JsonProperty("mailBody")
    private String mailBody;

    @JsonProperty("server")
    private Server server;

    @JsonProperty("status")
    private boolean status;

    public Application() {
        // Empty Constructor
    }

    public Application(long id, String name) {
        this.id = id;
        this.name = name;
    }

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    @Json
    public List<String> getRecipientList() {
        return recipientList;
    }

    @Json
    public List<String> getAttributeList() {
        return attributeList;
    }

    public void setId(long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }

    @Json
    public void setRecipientList(List<String> recipientList) {
        this.recipientList = recipientList;
    }

    @Json
    public void setAttributeList(List<String> attributeList) {
        this.attributeList = attributeList;
    }

    public String getMailSubject() {
        return mailSubject;
    }

    public void setMailSubject(String mailSubject) {
        this.mailSubject = mailSubject;
    }

    public String getMailBody() {
        return mailBody;
    }

    public void setMailBody(String mailBody) {
        this.mailBody = mailBody;
    }

    public Server getServer() {
        return server;
    }

    public void setServer(Server server) {
        this.server = server;
    }

    public boolean getStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
