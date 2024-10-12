package id.tsi.mmw.controller;

import id.tsi.mmw.model.Authentication;
import id.tsi.mmw.model.Pagination;
import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class UserController extends BaseController {

    public UserController() {
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
                " FROM user " +
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

    /**
     * Validates the user UID by checking if it exists in the database table.
     *
     * @param userUid The user UID to validate
     * @return true if the user UID exists, false otherwise
     */
    public boolean validateUserUid(String userUid) {
        final String methodName = "validateUserUid";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM user " +
                " WHERE  uid = :userUid;";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("userUid", userUid);
            result = q.mapTo(Boolean.class).one();

        } catch (Exception e) {
            log.error(methodName, e);
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
    public User getUserDetailByEmail(String email) {
        final String methodName = "getUserDetailByEmail";
        start(methodName);

        // Define the SQL query to fetch user details based on the provided email
        String sql = "SELECT uid, firstname, lastname, fullname, email, mobile_number, dob, status, create_dt, modify_dt " +
                "FROM user WHERE email = :email;";

        User user = null;

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("email", email);
            user = q.mapToBean(User.class).one();
        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return user;
    }

    public List<User> getUserList() {
        final String methodName = "getUserList";
        start(methodName);
        List<User> result = new ArrayList<>();

        String sql = "SELECT u.uid, u.fullname, u.email, u.department, u.status, u.create_dt, u.modify_dt, " +
                " ag.uid AS access_group_uid, ag.display_name AS access_group_name " +
                " FROM user u " +
                " LEFT JOIN user_access_group uag ON uag.user_uid = u.uid " +
                " LEFT JOIN access_group ag ON ag.uid = uag.access_group_uid " +
                " ORDER BY u.fullname ASC;";

        log.debug(methodName, "SQL : " + sql);
        // Bind the limit and offset parameters to the query
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            // Execute the query and get the result as a list of User objects
            result = q.mapToBean(User.class).list();
        } catch (Exception e) {
            // Log any SQL exception that occurs during the authentication process
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean create(User user) {
        final String methodName = "create";
        start(methodName);
        boolean result = false;

        try (Handle h = getHandle()) {
            // Execute the operations within a transaction
            result = h.inTransaction(handle -> createUserBatch(handle, user));
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
     * @param user the user object to insert
     * @return true if the insertion was successful, false otherwise
     */
    private boolean createUserBatch(Handle handle, User user){
        String sql = "INSERT INTO user " +
                "(uid, firstname, lastname, fullname, department, email, mobile_number, dob, status, create_dt) " +
                "VALUES" +
                "( :uid, :firstname, :lastname, :fullname, :department, :email, :mobileNumber, :dob, :status, :createDt);";
        PreparedBatch insertUser = handle.prepareBatch(sql);
        insertUser.bindBean(user);
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

    public boolean update(User user) {
        final String methodName = "update";
        start(methodName);
        boolean result = false;
        final String sql =
                "UPDATE user SET firstname = :firstname, lastname = :lastname, fullname = :fullname, " +
                        " mobile_number = :mobileNumber, dob =:dob, department = :department, modify_dt =:modifyDt " +
                        " WHERE uid = :uid";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(user);
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
        final String sql = "DELETE FROM user WHERE uid = :uid";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("uid", uid);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public User getUserByUid(String userUid) {
        final String methodName = "getUserByUid";
        start(methodName);

        User user = new User();
        final String sql = "SELECT uid, firstname, lastname, department, email, mobile_number, dob" +
                " FROM user " +
                " WHERE uid =  :userUid";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("userUid", userUid);
            user = q.mapToBean(User.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return user;
    }

}
