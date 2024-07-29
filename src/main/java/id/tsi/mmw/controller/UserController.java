package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
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
    public boolean validateEmail(String email)
    {
        final String methodName = "validateEmail";
        start(methodName);

        // Initialize the result variable to false
        boolean result = false;

        // Define the SQL query to check if the email exists in the 'users' table
        String sql = "SELECT if(COUNT(*)>0,'true','false') FROM users WHERE  email = :email;";

        // Get a database connection handle and create a query
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {

            // Bind the email parameter to the query
            q.bind("email", email);

            // Execute the query and retrieve the validation result as a boolean
            result = q.mapTo(Boolean.class).one();

        } catch (SQLException e) {
            // Log any SQL exception that occurs
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
                "FROM users WHERE email = :email;";

        // Initialize the user object to null
        User user = null;

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            // Bind the email parameter to the SQL query
            q.bind("email", email);

            // Execute the query and map the result to a User object
            user = q.mapToBean(User.class).one();
        } catch (SQLException e) {
            // Log any SQL exception that occurs during the query execution
            log.error(methodName, e);
        }

        completed(methodName);
        return user;
    }

    public List<User> getUserList()
    {
        final String methodName = "getUserList";
        start (methodName);
        List<User> result = new ArrayList<>();
        String sql = "Select uid, firstname, lastname, fullname, email, mobile_number, dob, status, create_dt, modify_dt from users";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            // Execute the query and get the result as a Boolean
            result = q.mapToBean(User.class).list();
        } catch (SQLException e) {
            // Log any SQL exception that occurs during the authentication process
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }
    public boolean create(User user)
    {
        final String methodName = "create";
        start (methodName);
        boolean result = false;
        final String sql = "INSERT INTO [users] (uid, firstname, lastname, fullname, email, mobile_number, dob, status, create_dt, modify_dt)values (:uid, :firstname, :lastname, :fullname, :email, :mobile_number, :dob, :status, :create_dt, :modify_dt)";

        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(user);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;


    }
    public boolean update(User user) {
        final String methodName = "update";
        start(methodName);
        boolean result = false;
        final String sql =
                "UPDATE [users] SET uid =:uid, firstname =:firstname, lastname =:lastname, fullname =:fullname, email =:email, mobile_number =:mobile_number , dob =:dob, status =:status, create_dt =:create_dt , modify_dt =:modify_dt ";
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
        final String sql = "DELETE FROM [users] WHERE uid = :uid";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("uid", uid);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
    public Optional<User> get(String uid) {
        final String methodName = "get";
        start(methodName);
        Optional<User> result = Optional.empty();
        final String sql = "SELECT * FROM [users] WHERE uid = :uid";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind("uid", uid).mapToBean(User.class).findOne();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

}
