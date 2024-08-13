package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MasterServiceController extends BaseController{
    public MasterServiceController () {
        log = getLogger(this.getClass());
    }

    public List<User> getUserList() {
        final String methodName = "getUserList";
        start(methodName);
        List<User> result = new ArrayList<>();
        String sql = "select service_id , service_name , service_description , service_price , service_status , create_dt , modify_dt  from master_service";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            /* Execute the query and get the result as a Boolean */
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
        final String sql = "INSERT INTO [master_service] (service_id , service_name , service_description , service_price , service_status , create_dt , modify_dt)values (:service_id , :service_name , :service_description , :service_price , :service_status , :create_dt , ;modify_dt)";

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
                "UPDATE [master_service] SET service_id =:service_id , service_name =:service_name, service_description =:service_description, service_price =:service_price, service_status =:service_status, create_dt =:create_dt , modify_dt =:modify_dt";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(user);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean delete(String service_id) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM [master_service] WHERE service_id = :service_id";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("service_id", service_id);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public User getUserByUid(String service_id) {
        final String methodName = "getUserByUid";
        start(methodName);

        User user = new User();
        final String sql = "SELECT service_id , service_name , service_description , service_price , service_status , create_dt , modify_dt from master_service" +
                " FROM master_service WHERE service_id = :service_id;
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("service_id", service_id);
            user = q.mapToBean(User.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return user;
    }
}
