package id.tsi.mmw.controller;

import id.tsi.mmw.model.Department;
import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.ArrayList;
import java.util.List;

@Controller
public class DepartmentController extends BaseController {

    public DepartmentController() {
        log = getLogger(this.getClass());
    }

    /**
     * Retrieves a list of departments from the database, ordered by their unique identifier in ascending order.
     *
     * This method executes a SQL query to fetch the department data, logs the query and any errors,
     * and returns the list of departments as Department objects.
     *
     * @return a list of Department objects
     */
    public List<Department> getDepartmentList() {
        final String methodName = "getDepartmentList";
        start(methodName);
        List<Department> result = new ArrayList<>();

        // SQL query to fetch the list of departments, ordered by their unique identifier in ascending order
        String sql = "SELECT uid, display_name, create_dt, modify_dt " +
                " FROM department " +
                " ORDER BY uid ASC;";

        log.debug(methodName, "SQL : " + sql);
        // Bind the limit and offset parameters to the query
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            // Execute the query and get the result as a list of Department objects
            result = q.mapToBean(Department.class).list();
        } catch (Exception e) {
            // Log any SQL exception that occurs during the authentication process
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }
}
