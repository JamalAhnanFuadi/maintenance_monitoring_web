package id.tsi.mmw.rest.service;

import id.tsi.mmw.manager.PropertyManager;
import id.tsi.mmw.property.Constants;
import id.tsi.mmw.property.Property;
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
    // START of logging management
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

    // END of logging management

    // START of property management
    protected String getProperty(String key) {
        return PropertyManager.getInstance().getProperty(key);
    }
    protected int getIntegerProperty(String key) {
        return PropertyManager.getInstance().getIntProperty(key);
    }
    protected boolean getBooleanProperty(String key) {
        return PropertyManager.getInstance().getBoolProperty(key);
    }
    // END of property management

    // START ofSession management
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
    // END of Session management

    // START of Response management
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
    protected Response buildBadRequestResponse(String message) {
        return buildResponse(Response.Status.BAD_REQUEST, message);
    }
    protected Response buildConflictResponse(String message) {
        return buildResponse(Response.Status.CONFLICT, message);
    }

    // END of Response Management

    /**
     * This function takes a page number and page size and returns the starting
     * offset for the pagination query.
     *
     * The formula is (pageNumber - 1) * pageSize. This is because the page number
     * is 1-indexed, and the offset is 0-indexed.
     *
     * For example, if we have a page size of 10, and we want to get the second
     * page, the offset would be 10.
     *
     * @param pageNumber The page number to get the offset for
     * @param pageSize The page size
     * @return The starting offset for the pagination query
     */
    protected int paginationOffsetBuilder(int pageNumber, int pageSize) {
        return (pageNumber - 1) * pageSize;
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

