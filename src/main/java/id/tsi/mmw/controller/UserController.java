package id.tsi.mmw.controller;

import id.tsi.mmw.model.Pagination;
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
    public boolean validateEmail(String email) {
        final String methodName = "validateEmail";
        start(methodName);

        // Initialize the result variable to false
        boolean result = false;

        // Define the SQL query to check if the email exists in the 'users' table
        String sql = "SELECT if(COUNT(*)>0,'true','false') FROM user WHERE  email = :email;";

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
                "FROM user WHERE email = :email;";

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


    /**
     * Retrieves a list of users with pagination. The list is sorted by the provided sort by and order.
     * A search filter can be provided to filter the results.
     *
     * @param pageSize       The maximum number of records to return
     * @param offset     The offset of the first record to return
     * @param sortBy         The field to sort by
     * @param sortOrder      The order to sort by (ASC or DESC)
     * @param searchFilter   The search filter to apply to the results
     * @return A list of User objects
     */
    public List<User> getUserListPagination(int pageSize, int offset, String sortBy, String sortOrder, String searchFilter) {
        final String methodName = "getUserList";
        start(methodName);
        List<User> result = new ArrayList<>();

        // Define the SQL query to fetch the users. The query is a bit complex because we need to
        // LEFT JOIN the user_access_group table to get the access group ID and name.
        // We also need to sort the results by the provided sort by and order.
        // If a search filter is provided, we need to add a WHERE clause to filter the results.
        String sql = " SELECT u.uid, u.firstname, u.lastname, u.fullname, u.email, "
                + " u.mobile_number, u.dob, u.status, u.create_dt, u.modify_dt, "
                + " ag.uid AS access_group_uid, ag.display_name AS access_group_name "
                + " FROM user u "
                + " LEFT JOIN user_access_group uag ON uag.user_uid = u.uid "
                + " LEFT JOIN access_group ag ON ag.uid = uag.access_group_uid ";

        // If a search filter is provided, add a WHERE clause to filter the results
        String whereClause = "";
        if (searchFilter != null && !searchFilter.isEmpty()) {
            whereClause = " WHERE u.fullname LIKE '%" + searchFilter + "%' ";
        }

        // Add the ORDER BY and LIMIT clauses
        sql += whereClause + " ORDER BY u." + sortBy + " " + sortOrder +  " LIMIT :pageSize OFFSET :offset";

        log.debug(methodName, "SQL : " + sql);
        // Bind the limit and offset parameters to the query
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("pageSize", pageSize);
            q.bind("offset", offset);
            // Execute the query and get the result as a list of User objects
            result = q.mapToBean(User.class).list();
        } catch (Exception e) {
            // Log any SQL exception that occurs during the authentication process
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    /**
     * Counts the total number of records and pages of users given a search filter.
     *
     * @param pageSize The page size to use for the pagination
     * @param searchFilter The search filter to apply to the results
     * @return A Pagination object containing the total number of records and the total number of pages
     */
    public Pagination countTotalRecordsAndPages(int pageSize, String searchFilter) {

        final String methodName = "countTotalRecordsAndPages";
        start(methodName);

        // Define the SQL query to fetch the total number of records and the total number of pages
        String sql = "SELECT COUNT(DISTINCT u.uid) AS totalRecords, CEILING(CAST(COUNT(DISTINCT u.uid) AS FLOAT) / :pageSize) AS totalPages FROM user u ";

        // Add a WHERE clause if a search filter is provided
        String whereClause = buildSearchFilter(searchFilter);
        sql += whereClause;

        // Log the final SQL query
        log.debug(methodName, "SQL :" +sql);
        Pagination result = new Pagination();
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            // Bind the pageSize parameter to the query
            q.bind("pageSize", pageSize);

            // Execute the query and map the result to a Pagination object
            // The result should contain the total number of records and the total number of pages
            result = q.mapToBean(Pagination.class).one();
        } catch (Exception ex) {
            // Log any SQL exception that occurs during the query execution
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    /**
     * Builds a WHERE clause for a SQL query based on a search filter.
     *
     * If the search filter is null or empty, an empty string is returned.
     * Otherwise, a WHERE clause is constructed with a LIKE operator to search for
     * the filter string in the user's fullname field.
     *
     * @param searchFilter the search filter to apply to the results
     * @return a WHERE clause as a String
     */
    private String buildSearchFilter(String searchFilter) {
        String filter = "";
        if (searchFilter != null && !searchFilter.isEmpty()) {
            filter = " WHERE u.fullname LIKE '%" + searchFilter + "%' ";
        }
        return filter;
    }

    public List<User> getUserList() {
        final String methodName = "getUserList";
        start(methodName);
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

    public boolean create(User user) {
        final String methodName = "create";
        start(methodName);
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

    public User getUserByUid(String uid) {
        final String methodName = "getUserByUid";
        start(methodName);

        User user = new User();
        final String sql = "SELECT uid, firstname, lastname, fullname, email, mobile_number, dob, status, create_dt, modify_dt" +
                " FROM users WHERE uid = :uid";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("uid", uid);
            user = q.mapToBean(User.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return user;
    }

}
