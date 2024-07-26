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

import java.util.List;
import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.ic.umx.controller.ApplicationController;
import sg.ic.umx.controller.IDGController;
import sg.ic.umx.controller.SettingController;
import sg.ic.umx.idg.model.UserFieldsResponse;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Server;
import sg.ic.umx.rest.model.ApplicationListResponse;
import sg.ic.umx.rest.model.ApplicationResponse;
import sg.ic.umx.util.constant.SettingName;
import sg.ic.umx.util.helper.StringHelper;

@Singleton
@PermitAll
@Path("applications")
@Produces(MediaType.APPLICATION_JSON)
public class ApplicationService extends BaseService {

    private final ApplicationController applicationController;
    private final SettingController settingController;
    private final IDGController idgController;

    public ApplicationService() {
        log = getLogger(this.getClass());
        applicationController = new ApplicationController();
        settingController = new SettingController();
        idgController = new IDGController();
    }

    @GET
    public Response list() {
        final String methodName = "list";
        start(methodName);
        ApplicationListResponse response = new ApplicationListResponse();

        List<Application> appList = applicationController.list();
        appList.forEach(app -> app.setServer(getServer(app.getServer().getId())));
        response.setApplicationList(appList);

        completed(methodName);
        return buildSuccessResponse(response);
    }

    @GET
    @Path("active")
    public Response listActive() {
        final String methodName = "listActive";
        start(methodName);
        ApplicationListResponse response = new ApplicationListResponse();

        List<Application> appList = applicationController.listByStatus(true);
        appList.forEach(app -> app.setServer(getServer(app.getServer().getId())));
        response.setApplicationList(appList);

        completed(methodName);
        return buildSuccessResponse(response);
    }

    @GET
    @Path("servers/{serverId}")
    public Response listByServerId(@PathParam("serverId") String serverId) {
        final String methodName = "listByServerId";
        start(methodName);
        ApplicationListResponse response = new ApplicationListResponse();
        response.setApplicationList(applicationController.listByServerId(serverId));
        completed(methodName);
        return buildSuccessResponse(response);
    }

    @GET
    @Path("{applicationId}")
    public Response get(@PathParam("applicationId") long applicationId) {
        ApplicationResponse response = new ApplicationResponse();

        Application app = applicationController.get(applicationId);

        log.info("get", app);

        app.setServer(getServer(app.getServer().getId()));
        response.setApplication(app);
        return buildSuccessResponse(response);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response create(Application application) {
        final String methodName = "create";
        start(methodName);

        if (!validateApplication(application)) {
            log.info(methodName, "Invalid Request");
            completed(methodName);
            return buildInvalidRequestResponse();
        }

        // Check if Application already exists
        if (applicationController.getCountByName(application.getName()) > 0) {
            log.info(methodName, "Resource Already Exists");
            completed(methodName);
            return getConflictedResponse("Resource Already Exists");
        }

        // populate mail subject with default if empty
        if (!StringHelper.validate(application.getMailSubject())) {
            application.setMailSubject(settingController.get(SettingName.MAIL_SUBJECT).getValue());
        }

        // populate mail body with default if empty
        if (!StringHelper.validate(application.getMailBody())) {
            application.setMailBody(settingController.get(SettingName.MAIL_CONTENT).getValue());
        }

        boolean result = applicationController.create(application);

        completed(methodName);

        return result ? buildSuccessResponse() : getErrorResponse();
    }

    @PUT
    @Path("{applicationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("applicationId") long applicationId, Application application) {
        final String methodName = "update";
        start(methodName);
        application.setId(applicationId);
        boolean result = applicationController.update(application);
        completed(methodName);
        return result ? buildSuccessResponse() : getErrorResponse();
    }

    @DELETE
    @Path("{applicationId}")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("applicationId") long applicationId) {
        final String methodName = "delete";
        start(methodName);
        boolean result = applicationController.delete(applicationId);
        completed(methodName);
        return result ? buildSuccessResponse() : getErrorResponse();
    }

    @GET
    @Path("{applicationId}/users/fields")
    @Produces(MediaType.APPLICATION_JSON)
    public Response listFields(@PathParam("applicationId") long applicationId) {
        final String methodName = "listFields";
        start(methodName);
        Response response = buildInvalidRequestResponse();
        Application application = applicationController.get(applicationId);

        if (application != null) {
            Server server = getServer(application.getServer().getId());

            if (server != null) {
                UserFieldsResponse fieldResponse =
                        idgController.getUserFields(server, application.getConfigurationName());

                response = buildSuccessResponse(fieldResponse);
            }
        }

        completed(methodName);
        return response;
    }

    private boolean validateApplication(Application application) {
        return StringHelper.validate(application.getName()) && StringHelper.validate(application.getConfigurationName())
                && application.getServer() != null && StringHelper.validate(application.getServer().getId())
                && application.getAttributeList() != null && !application.getAttributeList().isEmpty();
    }
}
