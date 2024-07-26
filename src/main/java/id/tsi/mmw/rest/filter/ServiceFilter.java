package id.tsi.mmw.rest.filter;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import javax.annotation.security.PermitAll;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.ws.rs.container.ContainerRequestContext;
import javax.ws.rs.container.ContainerRequestFilter;
import javax.ws.rs.container.ResourceInfo;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.ext.Provider;
import id.tsi.mmw.filter.ApplicationFilter;
import id.tsi.mmw.model.Principal;
import id.tsi.mmw.rest.exception.UnauthorizedException;
import id.tsi.mmw.rest.security.APIKey;

@Provider
public class ServiceFilter implements ContainerRequestFilter {

    @Context
    private ResourceInfo resourceInfo;

    @Context
    private HttpServletRequest httpServletRequest;

    @Context
    private HttpHeaders httpHeaders;

    public ServiceFilter() {
        // Empty Constructor
    }

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {

        boolean allow = false;

        if (hasSecurityAnnotations(resourceInfo)) {

/*            if (hasAnnotation(resourceInfo, APIKey.class)
                    && StringHelper.validate(httpHeaders.getHeaderString(SecurityConstant.API_KEY_HEADER))) {

                String apiKey = httpHeaders.getHeaderString(SecurityConstant.API_KEY_HEADER);

                allow = checkAPIKey(apiKey);

            } else */
            if (hasValidSession(httpServletRequest)) { // Permit All
                allow = true;
            }
        } else {
            allow = true;
        }

        if (!allow) {
            throw new UnauthorizedException();
        }

    }

/*    private boolean checkAPIKey(String apiKey) {
        Setting setting = settingController.get(SettingName.CE_API_KEY);
        return setting.getValue().equals(apiKey);
    }*/

    private boolean hasValidSession(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        return session != null && session.getAttribute(Principal.class.getCanonicalName()) != null
                && session.getAttribute(ApplicationFilter.SESSION_KEY) != null;
    }

    private boolean hasSecurityAnnotations(ResourceInfo resourceInfo) {
        return hasAnnotation(resourceInfo, PermitAll.class) || hasAnnotation(resourceInfo, APIKey.class);
    }

    private boolean hasAnnotation(ResourceInfo resourceInfo, Class<? extends Annotation> annotationClass) {
        Method method = resourceInfo.getResourceMethod();
        Class<?> clazz = resourceInfo.getResourceClass();
        return method.isAnnotationPresent(annotationClass) || clazz.isAnnotationPresent(annotationClass);
    }
}
