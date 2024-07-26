package id.tsi.mmw.filter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ApplicationFilter implements Filter {

    public static final String SESSION_KEY = "umx-session-key";

    private List<String> unprotectedList;

    @Override
    public void init(FilterConfig config) throws ServletException {
        unprotectedList = new ArrayList<>();
        unprotectedList.add("/login");
    }

    @Override
    public void destroy() {
        // Do Nothing
    }

    private boolean isProtectedPath(String path) {
        boolean protectedPath = true;

        for (String pathUrl : unprotectedList) {
            if (path.equalsIgnoreCase(pathUrl)) {
                protectedPath = false;
                break;
            }
        }
        return protectedPath;
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain)
            throws IOException, ServletException {

        // Initialize a boolean flag to track validity
        boolean valid = true;

        // Cast the servlet request to HttpServletRequest
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        // Extract the path from the request URI
        String path = request.getRequestURI().substring(request.getContextPath().length());

        // Check if the path contains any of the specified directories
        if (path.contains("/lib/") || path.contains("/js/") || path.contains("/css") || path.contains("/images/")
                || path.contains("/asset/") || path.contains("/rest/")) {
            // If it does, allow the request to pass through
            chain.doFilter(servletRequest, servletResponse);
        } else {
            // If the path is not in the specified directories, check if it is a protected path
            if (isProtectedPath(path)) {
                // If it is a protected path, check if the user has a valid session
                HttpSession session = request.getSession(false);
                if (!(session != null && session.getAttribute(SESSION_KEY) != null)) {
                    // If the session is not valid, set the flag to false
                    valid = false;
                }
            }

            // If the path is not in the specified directories and is valid, allow the request to pass through
            if (valid) {
                chain.doFilter(servletRequest, servletResponse);
            } else {
                // If the path is not valid, redirect to the login page
                HttpServletResponse response = (HttpServletResponse) servletResponse;
                response.sendRedirect(request.getContextPath() + "/login");
            }
        }
    }
}
