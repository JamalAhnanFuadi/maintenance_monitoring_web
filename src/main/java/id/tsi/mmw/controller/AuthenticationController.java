package id.tsi.mmw.controller;

import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;

@Controller
public class AuthenticationController extends BaseController {

    public AuthenticationController() {
        log = getLogger(this.getClass());
    }

    //SELECT uid, salt, password, login_allowed, create_dt, last_password_set, last_login_dt FROM authentications WHERE uid = :uid;

    public String getUserSalt(String uid) {
        final String methodName = "getUserSalt";
        start(methodName);
        String result = null;

        String sql = "SELECT salt FROM authentications WHERE uid = :uid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapTo(String.class).one();

        } catch (SQLException e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }

    public boolean authenticateUser(String uid, String hashedPassword) {
        final String methodName = "authenticateUser";
        start(methodName);
        boolean result = false;

        String sql = "SELECT if(COUNT(*)>0,'true','false') AS Result FROM authentications " +
                " WHERE uid = :uid AND password_hash = :password;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            q.bind("password", hashedPassword);

            result = q.mapTo(Boolean.class).one();
        } catch (SQLException e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }


    public void updateLoginTimestamp(String uid, String processingTime) {
        final String methodName = "updateLoginTimestamp";
        start(methodName);

        String sql = "UPDATE authentications " +
                "SET last_login_dt = :processingTime " +
                "WHERE uid= :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bind("processingTime", processingTime);
            u.bind("uid", uid);

            executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }
        completed(methodName);
    }
}
