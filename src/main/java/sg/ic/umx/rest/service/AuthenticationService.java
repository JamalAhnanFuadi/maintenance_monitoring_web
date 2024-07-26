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

import java.net.URI;
import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.servlet.http.HttpSession;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import sg.ic.umx.filter.ApplicationFilter;
import sg.ic.umx.manager.EncryptionManager;
import sg.ic.umx.model.Principal;
import sg.ic.umx.rest.model.AuthenticationRequest;
import sg.ic.umx.util.property.Property;

@Singleton
@Path("authentication")
@Produces(MediaType.APPLICATION_JSON)
public class AuthenticationService extends BaseService {

    private final String administratorUsername;
    private final String administratorPassword;

    public AuthenticationService() {
        administratorUsername = getProperty(Property.ADMIN_USERNAME);
        administratorPassword = getProperty(Property.ADMIN_PASSWORD);
    }

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(AuthenticationRequest authRequest) {

        if (administratorUsername.equals(authRequest.getUsername())
                && administratorPassword.equals(EncryptionManager.encrypt(authRequest.getPassword()))) {

            clearSession();
            HttpSession session = request.getSession(true);

            Principal principal = new Principal(authRequest.getUsername());
            session.setAttribute(Principal.class.getCanonicalName(), principal);
            session.setAttribute(ApplicationFilter.SESSION_KEY, principal);
            return buildSuccessResponse();

        } else {
            return Response.status(Status.UNAUTHORIZED).build();
        }
    }

    private void clearSession() {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    @GET
    @Path("logout")
    @PermitAll
    public Response logout() {
        clearSession();
        return Response.temporaryRedirect(URI.create(request.getContextPath() + "/login")).build();
    }

    @GET
    @Path("session")
    @PermitAll
    public Response session() {
        return buildSuccessResponse();
    }

}
