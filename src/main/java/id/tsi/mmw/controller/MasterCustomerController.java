package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MasterCustomerController extends BaseController{
    public MasterCustomerController() {
        log = getLogger(this.getClass());
    }

    public List<User> getUserList() {
        final String methodName = "getUserList";
        start(methodName);
        List<User> result = new ArrayList<>();
        String sql = "select CustomerID,FirstName,LastName,Email,PhoneNumber,Address,City ,State ,PostalCode,Country,DateOfBirth,Status from master_customer";
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
        final String sql = "INSERT INTO [master_customer] (CustomerID,FirstName,LastName,Email,PhoneNumber,Address,City ,State ,PostalCode,Country,DateOfBirth,Status)values (:CustomerID,:FirstName,:LastName,:Email,:PhoneNumber,:Address,:City ,:State ,:PostalCode,:Country,:DateOfBirth,:Status)";

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
                "UPDATE [master_customer] SET CustomerID =:CustomerID,FirstName =:FirstName,LastName =:LastName,Email =:Email,PhoneNumber =:PhoneNumber,Address =:Address,City =:City ,State =:State,PostalCode =:PostalCode,Country =:Country,DateOfBirth =:DateOfBirth,Status =:Status ";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(user);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
    public boolean delete(String CustomerID) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM [master_customer] WHERE CustomerID = :CustomerID";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("CustomerID", CustomerID);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
    public User getUserByCustomerID(String CustomerID) {
        final String methodName = "getUserByCustomerID";
        start(methodName);

        User user = new User();
        final String sql = "SELECT CustomerID,FirstName,LastName,Email,PhoneNumber,Address,City ,State ,PostalCode,Country,DateOfBirth,Status from master_customer" +
                " FROM master_customer WHERE CustomerID = :CustomerID";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("CustomerID", CustomerID);
            user = q.mapToBean(User.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return user;
    }
}
