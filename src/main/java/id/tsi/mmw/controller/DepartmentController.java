package id.tsi.mmw.controller;

import id.tsi.mmw.model.Department;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class DepartmentController extends BaseController {

    public DepartmentController() {
        log = getLogger(this.getClass());
    }

    public List<Department> getDepartmentList() {
        final String methodName = "getDepartmentList";
        start(methodName);
        List<Department> result = new ArrayList<>();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt, " +
                " CASE WHEN EXISTS (SELECT 1 FROM staff s WHERE s.department_uid = d.uid ) THEN TRUE ELSE FALSE END AS locked " +
                " FROM department d" +
                " WHERE d.display_name NOT IN ('ROOT USER') " +
                " ORDER BY d.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(Department.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public Department getDepartment(String uid) {
        final String methodName = "getDepartment";
        start(methodName);
        Department result = new Department();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt " +
                " FROM department d " +
                " WHERE d.uid = :uid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(Department.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addDepartment(Department department) {
        final String methodName = "addDepartment";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO department " +
                "(uid, display_name, description, create_dt) " +
                "VALUES( LOWER(UUID()), :displayName, :description, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(department);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateDepartment(Department department) {
        final String methodName = "updateDepartment";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE department " +
                " SET display_name = :displayName, description = :description, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(department);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateDepartment(String uid) {
        final String methodName = "validateDepartment";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM department " +
                " WHERE  uid = :uid;";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapTo(Boolean.class).one();

        } catch (Exception e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }


    public boolean delete(String uid) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM department WHERE uid = :uid";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("uid", uid);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
}
