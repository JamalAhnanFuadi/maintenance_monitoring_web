package id.tsi.mmw.controller;

import id.tsi.mmw.model.Application;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ApplicationController extends BaseController{

    /**
     * Retrieves a list of applications from the database, ordered by display name in ascending order.
     *
     * This method executes a SQL query to fetch the application data, logs the query and any errors,
     * and returns the list of applications as Application objects.
     *
     * @return a list of Application objects
     */
    public List<Application> getApplicationList() {
        // This method retrieves a list of applications from the database,
        // including their unique ID, display name, code, creation date, and modification date.
        // The results are ordered by display name in ascending order.
        final String methodName = "getApplicationList";
        start(methodName);
        List<Application> result = new ArrayList<>();

        String sql = "SELECT uid, display_name, code, create_dt, modify_dt " +
                " FROM application " +
                " ORDER BY display_name ASC;";

        log.debug(methodName, "SQL : " + sql);
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(Application.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }
}
