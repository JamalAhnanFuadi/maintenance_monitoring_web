package id.tsi.mmw.controller;

import id.tsi.mmw.model.User;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;

@Controller
public class UserController extends BaseController {

    public UserController() {
        log = getLogger(this.getClass());
    }

    public boolean validateEmail(String email)
    {
        final String methodName = "validateEmail";
        start(methodName);
        boolean result = false;

        String sql = "SELECT if(COUNT(*)>0,'true','false') FROM users WHERE  email = :email;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("email", email);
            result = q.mapTo(Boolean.class).one();
        } catch (SQLException e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }

    public User getUserDetailByEmail(String email) {
        final String methodName = "getUserDetailByEmail";
        start(methodName);

        String sql = "SELECT uid, firstname, lastname, fullname, email, mobile_number, dob, status, create_dt, modify_dt FROM users WHERE email = :email;";

        User user = null;

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("email", email);
            user = new User();
            user = q.mapToBean(User.class).one();
        } catch (SQLException e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return user;
    }
}
