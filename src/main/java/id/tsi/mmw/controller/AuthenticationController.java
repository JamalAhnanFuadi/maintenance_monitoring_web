package id.tsi.mmw.controller;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;

@Controller
public class AuthenticationController extends BaseController {

    public AuthenticationController() {
        log = getLogger(this.getClass());
    }

    /**
     * Retrieves the salt associated with a user from the 'authentications' table based on the provided UID.
     *
     * @param uid The unique identifier of the user
     * @return The salt value for the specified user
     */
    public String getUserSalt(String uid) {
        final String methodName = "getUserSalt";
        start(methodName);

        // Initialize the result variable
        String result = null;

        // SQL query to fetch the salt for the given UID from the database
        String sql = "SELECT salt FROM authentication WHERE uid = :uid;";

        // Get a database connection handle
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            // Bind the UID parameter to the query
            q.bind("uid", uid);

            // Execute the query and retrieve the salt value
            result = q.mapTo(String.class).one();

        } catch (SQLException e) {
            // Log any SQL exception that occurs
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    /**
     * Authenticates a user by checking if the provided UID and hashed password exist in the 'authentications' table
     * and have login permission.
     *
     * @param uid            The unique identifier of the user
     * @param hashedPassword The hashed password of the user
     * @return true if the user is authenticated, false otherwise
     */
    public boolean authenticateUser(String uid, String hashedPassword) {
        final String methodName = "authenticateUser";
        start(methodName);

        // Initialize the result as false
        boolean result = false;

        // SQL query to check if the user credentials are valid and login is allowed
        String sql = "SELECT if(COUNT(*)>0,'true','false') AS Result FROM authentication " +
                " WHERE uid = :uid AND password_hash = :password AND login_allowed = 1;";

        // Get a database connection handle
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            // Bind the UID and hashed password parameters to the query
            q.bind("uid", uid);
            q.bind("password", hashedPassword);

            // Execute the query and get the result as a Boolean
            result = q.mapTo(Boolean.class).one();
        } catch (SQLException e) {
            // Log any SQL exception that occurs during the authentication process
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    /**
     * Method to update the last login timestamp of a user identified by 'uid'.
     *
     * @param uid            The unique identifier of the user
     * @param processingTime The timestamp of the last login
     */
    public void updateLoginTimestamp(String uid, String processingTime) {
        final String methodName = "updateLoginTimestamp";
        start(methodName);

        // SQL query to update the 'last_login_dt' field in the 'authentications' table
        String sql = "UPDATE authentication " +
                "SET last_login_dt = :processingTime " +
                "WHERE uid= :uid;";

        // Get a database connection handle
        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            // Bind the 'processingTime' and 'uid' parameters to the update object
            u.bind("processingTime", processingTime);
            u.bind("uid", uid);

            // Execute the update operation
            executeUpdate(u);

        } catch (SQLException e) {
            // Log any SQL exception that occurs during the update operation
            log.error(methodName, e);
        }

        completed(methodName);
    }

    // Method to update the login allowed status for a user identified by uid
    public void updateLoginAllowed(String uid, boolean allowed) {
        final String methodName = "updateLoginAllowed";
        start(methodName);

        // SQL query to update the 'login_allowed' field in the 'authentications' table
        String sql = "UPDATE authentications " +
                "SET login_allowed = :allowed " +
                "WHERE uid= :uid;";

        // Get a database connection handle
        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            // Bind the 'allowed' and 'uid' parameters to the update object
            u.bind("allowed", allowed);
            u.bind("uid", uid);

            // Execute the update operation
            executeUpdate(u);

        } catch (SQLException e) {
            // Log any SQL exception that occurs during the update operation
            log.error(methodName, e);
        }

        completed(methodName);
    }
}
