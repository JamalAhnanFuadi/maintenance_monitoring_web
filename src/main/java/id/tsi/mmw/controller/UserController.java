package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.sql.SQLException;

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
}
