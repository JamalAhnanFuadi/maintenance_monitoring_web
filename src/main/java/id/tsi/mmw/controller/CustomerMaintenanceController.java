package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CustomerMaintenanceController extends BaseController {
    public CustomerMaintenanceController() {
        log = getLogger(this.getClass());
    }

    public List<User> getUserList() {
        final String methodName = "getUserList";
        start(methodName);
        List<User> result = new ArrayList<>();
        String sql = "select maintenance_id , equipment_id , maintenance_type , maintenance_date , status , description , performed_by , create_dt , modify_dt  from customer_maintenance ";
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
        final String sql = "INSERT INTO [customer_maintenance] (maintenance_id , equipment_id , maintenance_type , maintenance_date , status , description , performed_by , create_dt , modify_dt)values (:maintenance_id , :equipment_id , :maintenance_type , :maintenance_date , :status , :description , :performed_by , :create_dt , :modify_dt)";

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
                "UPDATE [customer_maintenance] SET maintenance_id = :maintenance_id, equipment_id = :equipment_id , maintenance_type = :maintenance_type, maintenance_date = :maintenance_date, status = :status, description = :description, performed_by = :performed_by, create_dt = :create_dt, modify_dt = :modify_dt";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(user);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
    public boolean delete(String maintenance_id) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM [customer_maintenance] WHERE maintenance_id = :maintenance_id";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("maintenance_id", maintenance_id);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
    public User getUserByCustomerID(String maintenance_id) {
        final String methodName = "getUserByMaintenanceId";
        start(methodName);

        User user = new User();
        final String sql = "SELECT maintenance_id , equipment_id , maintenance_type , maintenance_date , status , description , performed_by , create_dt , modify_dt from customer_maintenance" +
                " FROM customer_maintenance WHERE maintenance_id = :maintenance_id";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("maintenance_id", maintenance_id);
            user = q.mapToBean(User.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return user;
    }
}
}
