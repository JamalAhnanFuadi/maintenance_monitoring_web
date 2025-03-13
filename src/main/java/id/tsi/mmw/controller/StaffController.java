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

    /**
     * Validates the user email by checking if it exists in the database table.
     *
     * @param email The email address to validate
     * @return true if the email exists, false otherwise
     */
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
        String sql = "SELECT a.uid, a.firstname, a.lastname, a.mobile_number, a.email, b.display_name AS department, a.status, a.dob, a.create_dt, a.modify_dt " +
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
    public boolean validateUser(String uid) {
        final String methodName = "validateUser";
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



    public boolean create(Staff staff) {
        final String methodName = "create";
        start(methodName);
        boolean result = false;

        try (Handle h = getHandle()) {
            // Execute the operations within a transaction
            result = h.inTransaction(handle -> createUserBatch(handle, staff));
        } catch (Exception ex) {
            log.error(methodName, ex);
        }

        completed(methodName);
        return result;

    }

    /**
     * Inserts a user into the database using a prepared batch statement.
     *
     * @param handle the handle to the database connection
     * @param staff the user object to insert
     * @return true if the insertion was successful, false otherwise
     */
    private boolean createUserBatch(Handle handle, Staff staff){
        String sql = "INSERT INTO user " +
                "(uid, firstname, lastname, fullname, department, email, mobile_number, dob, status, create_dt) " +
                "VALUES" +
                "( :uid, :firstname, :lastname, :fullname, :department, :email, :mobileNumber, :dob, :status, :createDt);";
        PreparedBatch insertUser = handle.prepareBatch(sql);
        insertUser.bindBean(staff);
        return executeBatch(insertUser);
    }

    /**
     * Updates the 'status' field of a user with the specified 'uid' to the specified 'status'.
     *
     * @param uid    The unique identifier of the user to update
     * @param status The new status of the user (true for active, false for inactive)
     */
    public boolean updateUserStatus(String uid, boolean status) {
        final String methodName = "updateUserStatus";
        start(methodName);
        boolean result = false;
        // SQL query to update the 'status' field in the 'user' table
        String sql = "UPDATE user " +
                "SET status = :status " + // Set the 'status' field to the value of the 'status' parameter
                "WHERE uid= :uid;"; // Filter the update operation to the 'user' with the specified 'uid'

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            // Bind the 'status' and 'uid' parameters to the update object
            u.bind("status", status);
            u.bind("uid", uid);

            // Execute the update operation
            result = executeUpdate(u);

        } catch (SQLException e) {
            // Log any SQL exception that occurs during the update operation
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }

    public boolean update(Staff staff) {
        final String methodName = "update";
        start(methodName);
        boolean result = false;
        final String sql =
                "UPDATE user SET firstname = :firstname, lastname = :lastname, fullname = :fullname, " +
                        " mobile_number = :mobileNumber, dob =:dob, department = :department, modify_dt =:modifyDt " +
                        " WHERE uid = :uid";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(staff);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }





}
