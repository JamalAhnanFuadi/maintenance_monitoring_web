package id.tsi.mmw.controller;

import id.tsi.mmw.model.CustomerType;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class CustomerTypeController extends BaseController {

    public CustomerTypeController() {
        log = getLogger(this.getClass());
    }

    public List<CustomerType> getCustomerTypeList() {
        final String methodName = "getCustomerTypeList";
        start(methodName);
        List<CustomerType> result = new ArrayList<>();

        String sql = "SELECT ct.uid, ct.display_name, ct.description, ct.create_dt, ct.modify_dt " +
                " FROM customer_type ct " +
                " ORDER BY ct.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(CustomerType.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public CustomerType getCustomerType(String uid) {
        final String methodName = "getCustomerType";
        start(methodName);
        CustomerType result = new CustomerType();

        String sql = "SELECT ct.uid, ct.display_name, ct.description, ct.create_dt, ct.modify_dt " +
                " FROM customer_type ct " +
                " WHERE ct.uid = :uid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(CustomerType.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addCustomerType(CustomerType customerType) {
        final String methodName = "addCustomerType";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO customer_type " +
                "(uid, display_name, description, create_dt) " +
                "VALUES( LOWER(UUID()), :displayName, :description, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(customerType);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateCustomerType(CustomerType customerType) {
        final String methodName = "updateCustomerType";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE customer_type " +
                " SET display_name = :displayName, description = :description, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(customerType);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateCustomerType(String uid) {
        final String methodName = "validateCustomerType";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM customer_type " +
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


    public boolean deleteCustomerType(String uid) {
        final String methodName = "deleteCustomerType";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM customer_type WHERE uid = :uid";
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
