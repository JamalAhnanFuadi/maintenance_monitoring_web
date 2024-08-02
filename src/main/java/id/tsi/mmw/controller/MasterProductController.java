package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MasterProductController extends BaseController{

    public MasterProductController() {
        log = getLogger(this.getClass());
    }

    public List<User> getUserList() {
        final String methodName = "getUserList";
        start(methodName);
        List<User> result = new ArrayList<>();
        String sql = "Select uid, product_key, description, create_dt, modify_dt from master_product";
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
        final String sql = "INSERT INTO [master_product] (uid, product_key, description ,create_dt, modify_dt)values (:uid, :product_key, :description ,:create_dt, :modify_dt)";

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
                "UPDATE [master_product] SET uid =:uid, product_key =: product_key, description =: description ,create_dt =: create_dt, modify_dt =: modify_dt ";
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
        final String sql = "DELETE FROM [master_product] WHERE uid = :uid";
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
        final String sql = "SELECT uid, product_key, description ,create_dt, modify_dt from master_product" +
                " FROM master_product WHERE uid = :uid";
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
