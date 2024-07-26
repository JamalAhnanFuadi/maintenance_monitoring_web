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
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.ic.umx.controller.SettingController;
import sg.ic.umx.model.Setting;
import sg.ic.umx.rest.exception.InvalidRequestException;
import sg.ic.umx.rest.model.SettingListResponse;

@Singleton
@PermitAll
@Path("settings")
@Produces(MediaType.APPLICATION_JSON)
public class SettingService extends BaseService {

    private final SettingController settingController;

    public SettingService() {
        log = getLogger(this.getClass());
        settingController = new SettingController();
    }

    @GET
    public Response list() {
        SettingListResponse response = new SettingListResponse();
        response.setSettingList(settingController.list());
        return buildSuccessResponse(response);
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response update(List<Setting> settingList) {
        final String methodName = "update";
        start(methodName);

        if (settingList == null) {
            throw new InvalidRequestException();
        }

        boolean result = settingController.update(settingList);
        completed(methodName);
        return result ? buildSuccessResponse() : getErrorResponse();
    }

}
