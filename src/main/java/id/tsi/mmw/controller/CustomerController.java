package id.tsi.mmw.controller;

import id.tsi.mmw.model.Customer;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerController extends BaseController {

    public CustomerController() {
        log = getLogger(this.getClass());
    }

    public List<Customer> getCustomerList() {
        final String methodName = "getCustomerList";
        start(methodName);
        List<Customer> result = new ArrayList<>();

        String sql = "SELECT c.uid, c.display_name, c.email, c.phone, c.address, c.website, c.status, c.create_dt, c.modify_dt " +
                " FROM customer c " +
                " ORDER BY c.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(Customer.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public Customer getCustomer(String uid) {
        final String methodName = "getCustomer";
        start(methodName);
        Customer result = new Customer();

        String sql = "SELECT c.uid, c.display_name, c.email, c.phone, c.address, c.website, c.status, c.create_dt, c.modify_dt " +
                " FROM customer c " +
                " WHERE c.uid = :uid";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(Customer.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addCustomer(Customer customer) {
        final String methodName = "addCustomer";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO customer " +
                "(uid, display_name, email, phone, address, website, status, create_dt)" +
                " VALUES( LOWER(UUID()), :displayName, :email, :phone, :address, :website, 1, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(customer);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateCustomer(Customer customer) {
        final String methodName = "updateCustomer";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE customer " +
                " SET display_name = :displayName, email = :email, phone = :phone, address = :address, website = :website, status = :status, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(customer);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateCustomer(String uid) {
        final String methodName = "validateCustomer";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM customer " +
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


    public boolean deleteCustomer(String uid) {
        final String methodName = "deleteCustomer";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM customer WHERE uid = :uid";
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
