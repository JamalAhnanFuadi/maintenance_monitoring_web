package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MasterSubcriptionController extends BaseController{

        public MasterSubcriptionController() {
            log = getLogger(this.getClass());
        }

        public List<User> getUserList() {
            final String methodName = "getUserList";
            start(methodName);
            List<User> result = new ArrayList<>();
            String sql = "sub_id,sub_name,sub_description,sub_price ,subs_duration ,sub_type , create_dt, modify_dt from master_subscription";
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
            final String sql = "INSERT INTO [master_subcription] (sub_id,sub_name,sub_description,sub_price ,subs_duration ,sub_type , create_dt, modify_dt)values (:sub_id,:sub_name,:sub_description,:sub_price ,:subs_duration ,:sub_type , :create_dt, :modify_dt)";

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
                    "UPDATE [master_subcription] SET sub_id =:sub_id,sub_name =:sub_name,sub_description =:sub_description,sub_price =:sub_price ,subs_duration =:subs_duration,sub_type =:sub_type,create_dt =:create_dt, modify_dt =:modify_dt ";
            try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
                u.bindBean(user);
                result = executeUpdate(u);
            } catch (Exception ex) {
                log.error(methodName, ex);
            }
            completed(methodName);
            return result;
        }

        public boolean delete(String sub_id) {
            final String methodName = "delete";
            start(methodName);
            boolean result = false;
            final String sql = "DELETE FROM [master_subcription] WHERE sub_id = :sub_id";
            try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
                u.bind("sub_id", sub_id);
                result = executeUpdate(u);
            } catch (Exception ex) {
                log.error(methodName, ex);
            }
            completed(methodName);
            return result;
        }

        public User getUserByUid(String sub_id) {
            final String methodName = "getUserByuid";
            start(methodName);

            User user = new User();
            final String sql = "SELECT sub_id,sub_name,sub_description,sub_price ,subs_duration ,sub_type , create_dt, modify_dt from master_subcription" +
                    " FROM master_subcription WHERE sub_id = :sub_id";
            try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
                q.bind("sub_id", sub_id);
                user = q.mapToBean(User.class).one();
            } catch (Exception ex) {
                log.error(methodName, ex);
            }
            completed(methodName);
            return user;
        }
}
