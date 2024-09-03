package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MasterCompanyController extends BaseController{
    public MasterCompanyController() {
        log = getLogger(this.getClass());
    }

    public List<User> getUserList() {
        final String methodName = "getUserList";
        start(methodName);
        List<User> result = new ArrayList<>();
        String sql = "select CompanyID, CompanyName, Address, City, State, PostalCode, Country, PhoneNumber, Email from master_company";
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
        final String sql = "INSERT INTO [master_company] (CompanyID, CompanyName, Address, City, State, PostalCode, Country, PhoneNumber, Email)values (:CompanyID, :CompanyName, :Address, :City, :State, :PostalCode, :Country, :PhoneNumber, :Email)";

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
                "UPDATE [master_company] SET CompanyID =:CompanyID, CompanyName =:CompanyName, Address =:Address, City =:City, State =:State, PostalCode =:PostalCode, Country =:Country, PhoneNumber =:PhoneNumber, Email =:Email ";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(user);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
    public boolean delete(String CompanyID) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM [master_company] WHERE CompanyID = :CompanyID";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("CompanyID", CompanyID);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
    public User getUserByCompanyID(String CompanyID) {
        final String methodName = "getUserByCompanyID";
        start(methodName);

        User user = new User();
        final String sql = "SELECT  CompanyName, Address, City, State, PostalCode, Country, PhoneNumber, Email from master_company" +
                " FROM master_company WHERE CompanyID = :CompanyID";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("CompanyID", CompanyID);
            user = q.mapToBean(User.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return user;
    }

}
