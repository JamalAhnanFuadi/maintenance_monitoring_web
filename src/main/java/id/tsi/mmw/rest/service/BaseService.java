package id.tsi.mmw.rest.service;

import id.tsi.mmw.property.Constants;
import id.tsi.mmw.util.log.AppLogger;
import id.tsi.mmw.rest.model.response.ServiceResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;
import java.util.List;
import java.util.UUID;

public class BaseService {
    protected AppLogger log;

    @Context
    protected HttpServletRequest httpServletRequest;

    public BaseService() {
        // Empty Constructor
    }
    // Logging
    protected AppLogger getLogger(Class<?> clazz) {
        return new AppLogger(clazz);
    }
    protected static final String INVALID_REQUEST = "Invalid Request";

    protected void start(String methodName) {
        log.info(methodName, "start");
    }

    protected void completed(String methodName) {
        log.info(methodName, "completed");
    }

    // Session management
    protected void clearSession() {
        HttpSession session = httpServletRequest.getSession(false);
        if (session != null) {
            session.invalidate();
        }
    }

    protected String generateTrackingID() {
        return UUID.randomUUID().toString();
    }

    protected void setTrackingID(String trackingId) {
        setSessionAttribute(Constants.SESSION_TRACKING_ID, trackingId);
    }

    protected boolean hasSession() {
        return getSession() != null;
    }

    protected boolean hasSessionAttribute(String key) {
        return getSession() != null && getSession().getAttribute(key) != null;
    }

    protected HttpSession getSession() {
        return httpServletRequest.getSession(false);
    }

    protected void setSessionAttribute(String key, Object value) {
        HttpSession session;
        if (!hasSession()) {
            session = httpServletRequest.getSession(true);
        } else {
            session = getSession();
        }
        session.setAttribute(key, value);
    }

    protected <T> T getSessionAttribute(String key, Class<T> clazz) {
        if (hasSession() && getSession().getAttribute(key) != null) {
            return clazz.cast(getSession().getAttribute(key));
        }
        return null;
    }

    protected List<String> getSessionAttributeList(String key) {
        if (hasSession() && getSession().getAttribute(key) != null) {
            return (List<String>) getSession().getAttribute(key);
        }
        return null;
    }
    protected void removeSessionAttribute(String key) {
        if (hasSessionAttribute(key)) {
            getSession().removeAttribute(key);
        }
    }

    // Response management
    private Response buildResponse(Response.Status status, String message) {
        return Response.status(status).entity(new ServiceResponse(status, message)).build();
    }

    protected Response buildSuccessResponse() {
        return buildResponse(Response.Status.OK, "Success");
    }

    protected Response buildSuccessResponse(Object obj) {
        return Response.status(Response.Status.OK).entity(obj).build();
    }

    protected Response buildUnauthorizedResponse() {
        return buildResponse(Response.Status.UNAUTHORIZED, "Unauthorized");
    }
    protected Response buildBadRequestResponse() {
        return buildResponse(Response.Status.BAD_REQUEST, "Bad Request");
    }

    protected Response buildInvalidRequestResponse() {
        return buildResponse(Response.Status.BAD_REQUEST, INVALID_REQUEST);
    }

    protected Response getConflictedResponse(String message) {
        return buildResponse(Response.Status.CONFLICT, message);
    }
    protected Response getErrorResponse() {
        return buildResponse(Response.Status.INTERNAL_SERVER_ERROR, "Internal Server Error");
    }
}

