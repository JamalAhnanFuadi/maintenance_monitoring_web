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
package sg.ic.umx.rest.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import sg.ic.umx.engine.model.RuleViolation;
import sg.ic.umx.manager.CacheManager;
import sg.ic.umx.manager.PropertyManager;
import sg.ic.umx.model.Server;
import sg.ic.umx.rest.model.ServiceResponse;
import sg.ic.umx.util.csv.CSVRecord;
import sg.ic.umx.util.log.AppLogger;

public class BaseService {

    protected AppLogger log;
    protected static final String INVALID_REQUEST = "Invalid Request";

    @Context
    protected HttpServletRequest request;

    protected Response getErrorResponse() {
        return buildResponse(Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }

    protected Response buildResourceNotFoundResponse() {
        return buildResponse(Status.NOT_FOUND, "Resource Not Found");
    }

    protected Response getAcceptedResponse(String message) {
        return buildResponse(Status.ACCEPTED, message);
    }

    protected Response buildSuccessResponse() {
        return buildResponse(Status.OK, "Success");
    }

    protected Response buildSuccessResponse(Object obj) {
        return Response.status(Status.OK).entity(obj).build();
    }

    protected Response buildInvalidRequestResponse() {
        return buildResponse(Status.BAD_REQUEST, INVALID_REQUEST);
    }

    protected Response getConflictedResponse(String message) {
        return buildResponse(Status.CONFLICT, message);
    }

    private Response buildResponse(Status status, String message) {
        return Response.status(status).entity(new ServiceResponse(status, message)).build();
    }

    protected AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz);
    }

    protected Executor initThreadPool() {
        return Executors.newFixedThreadPool(30);
    }

    protected void start(String methodName) {
        log.debug(methodName, "start");
    }

    protected void completed(String methodName) {
        log.debug(methodName, "completed");
    }

    protected String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }

    protected String generateUUID() {
        return UUID.randomUUID().toString();
    }

    protected List<CSVRecord> convertRuleViolationToCSV(List<String> headerList, List<RuleViolation> violationList) {
        List<CSVRecord> recordList = new ArrayList<>();

        violationList.forEach(violation -> violation.getRoleList().forEach(role -> {
            CSVRecord record = new CSVRecord();
            record.put(headerList.get(0), violation.getUserId());
            record.put(headerList.get(1), violation.getAccountId());
            record.put(headerList.get(2), violation.getAccountType().getType());
            record.put(headerList.get(3), role);
            recordList.add(record);
        }));

        return recordList;
    }

    protected Server getServer(String serverId) {
        Map<String, Server> serverMap = CacheManager.getInstance().getServerMap();
        if (serverMap.containsKey(serverId)) {
            return serverMap.get(serverId);
        }
        return null;
    }



}
