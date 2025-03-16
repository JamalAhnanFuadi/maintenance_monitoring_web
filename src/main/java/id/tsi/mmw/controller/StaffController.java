package id.tsi.mmw.controller;

import id.tsi.mmw.model.Staff;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class StaffController extends BaseController {

    public StaffController() {
        log = getLogger(this.getClass());
    }

    public boolean validateEmail(String email) {
        final String methodName = "validateEmail";
        start(methodName);
        boolean result = false;

        // Define the SQL query to check if the email exists in the 'users' table
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM staff " +
                " WHERE  email = :email;";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("email", email);
            result = q.mapTo(Boolean.class).one();

        } catch (Exception e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }

    public Staff getStaff(String userUid) {
        final String methodName = "getStaff";
        start(methodName);

        Staff staff = new Staff();
        String sql = "SELECT a.uid, a.firstname, a.lastname, a.mobile_number, a.email, b.display_name AS department, a.department_uid, a.status, a.dob, a.create_dt, a.modify_dt " +
                " FROM staff a " +
                " LEFT JOIN department b ON a.department_uid = b.uid " +
                " WHERE a.uid = :userUid;";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("userUid", userUid);
            staff = q.mapToBean(Staff.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return staff;
    }

    public List<Staff> getStaffList() {
        final String methodName = "getStaffList";
        start(methodName);
        List<Staff> result = new ArrayList<>();

        String sql = "SELECT a.uid, a.firstname, a.lastname, b.display_name AS department, a.email, a.mobile_number, a.dob, a.photo_url, a.status, a.create_dt, a.modify_dt " +
                "FROM staff a " +
                "JOIN department b ON b.uid = a.department_uid  " +
                "WHERE a.email  NOT IN ('root@mail.com') " +
                "ORDER BY a.firstname ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(Staff.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }
    public boolean validateStaff(String uid) {
        final String methodName = "validateStaff";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM staff " +
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

    public boolean addStaff(Staff staff) {
        final String methodName = "addStaff";
        start(methodName);
        boolean result = false;
        // SQL query to update the 'status' field in the 'user' table
        String sql = "INSERT INTO staff " +
                "(uid, firstname, lastname, department_uid, email, mobile_number, dob, status, create_dt) " +
                "VALUES(:uid, :firstname, :lastname, :departmentUid, :email, :mobileNumber, :dob, 0, CURRENT_TIMESTAMP);" ;

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(staff);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }

    public boolean updateStaff(Staff staff) {
        final String methodName = "updateStaff";
        start(methodName);
        boolean result = false;
        final String sql =
                "UPDATE staff " +
                        "SET firstname = :firstname, lastname = :lastname, department_uid = :department, mobile_number = :mobileNumber, dob = :dob, status = :status, modify_dt = CURRENT_TIMESTAMP " +
                        "WHERE uid = :uid; ";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(staff);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean updateStaffStatus(String uid, boolean status) {
        final String methodName = "updateStaffStatus";
        start(methodName);
        boolean result = false;
        final String sql =
                "UPDATE staff SET status = :status, modify_dt = CURRENT_TIMESTAMP WHERE uid = :uid; ";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("status", status);
            u.bind("uid", uid);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }


    public boolean delete(String uid) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM staff WHERE uid = :uid";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("uid", uid);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }










    /**
     * Retrieves user details based on the provided email address.
     *
     * @param email The email address of the user
     * @return The User object containing user details, or null if not found
     */
    public Staff getUserDetailByEmail(String email) {
        final String methodName = "getUserDetailByEmail";
        start(methodName);

        // Define the SQL query to fetch user details based on the provided email
        String sql = "SELECT uid, firstname, lastname, email, mobile_number, dob, status, create_dt, modify_dt " +
                "FROM staff WHERE email = :email;";

        Staff staff = null;

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("email", email);
            staff = q.mapToBean(Staff.class).one();
        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return staff;
    }
}
