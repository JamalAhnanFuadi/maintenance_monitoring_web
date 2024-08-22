package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AccessMatricController extends BaseController{
    public AccessMatricController() {
        log = getLogger(this.getClass());
    }

    public List<User> getUserList() {
        final String methodName = "getUserList";
        start(methodName);
        List<User> result = new ArrayList<>();
        String sql = "Select access_id , user_id ,resource_id , access_level , granted_at , revoked_at  from access_matrix ";
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
        final String sql = "INSERT INTO [access_matrix] (access_id , user_id ,resource_id , access_level , granted_at , revoked_at)values (:access_id , :user_id ,:resource_id , :access_level , :granted_at , :revoked_at)";

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
                "UPDATE [access_matrix] SET access_id =:access_id , user_id =:user_id,resource_id =:resource_id, access_level =:access_level, granted_at =:granted_at, revoked_at =:revoked_at ";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(user);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean delete(String user_id) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM [access_matrix] WHERE user_id = :user_id";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("user_id", user_id);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public User getUserByUid(String user_id) {
        final String methodName = "getUserByUid";
        start(methodName);

        User user = new User();
        final String sql = "SELECT access_id , user_id ,resource_id , access_level , granted_at , revoked_at from access_matrix" +
                " FROM access_matrix WHERE access_matrix = :user_id";
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            q.bind("user_id", user_id);
            user = q.mapToBean(User.class).one();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return user;
    }
}
