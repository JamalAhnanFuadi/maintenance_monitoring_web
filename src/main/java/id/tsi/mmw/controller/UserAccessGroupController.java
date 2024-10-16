package id.tsi.mmw.controller;

import id.tsi.mmw.util.helper.DateHelper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.time.LocalDateTime;
import java.util.UUID;

@Controller
public class UserAccessGroupController extends BaseController{

    public UserAccessGroupController() {
        log = getLogger(this.getClass());
    }

    public boolean validateHasAccessGroup(String userUid) {
        final String methodName = "validateEmail";
        start(methodName);
        boolean result = false;

        // Define the SQL query to check if the email exists in the 'users' table
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM user_access_group " +
                " WHERE  user_uid = :userUid;";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("userUid", userUid);
            result = q.mapTo(Boolean.class).one();

        } catch (Exception e) {
            log.error(methodName, e);
        }
        completed(methodName);
        return result;
    }

    public boolean addUserToAccessGroup(String userUid, String accessGroupUid) {
        final String methodName = "addUserToAccessGroup";
        start(methodName);

        boolean insert = false;
        String sql = "INSERT INTO user_access_group " +
                "(uid, user_uid, access_group_uid, create_dt) " +
                "VALUES( :uid, :userUid, :accessGroupUid, :createDt);";

        log.debug(methodName, "SQL : " + sql);
        try (Handle handle = getHandle(); Update update = handle.createUpdate(sql)) {

            update.bind("uid", UUID.randomUUID().toString())
                    .bind("userUid", userUid)
                    .bind("accessGroupUid", accessGroupUid)
                    .bind("createDt", DateHelper.formatDBDateTime(LocalDateTime.now()));
            insert = executeUpdate(update);
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return insert;

    }
    public boolean UpdateUserToAccessGroup(String userUid, String accessGroupUid) {
        final String methodName = "UpdateUserToAccessGroup";
        start(methodName);

        boolean insert = false;
        String sql = "UPDATE user_access_group " +
                " SET  access_group_uid = :accessGroupUid, modify_dt = :modifyDt " +
                " WHERE user_uid = :userUid;";

        log.debug(methodName, "SQL : " + sql);
        try (Handle handle = getHandle(); Update update = handle.createUpdate(sql)) {

            update.bind("uid", UUID.randomUUID().toString())
                    .bind("userUid", userUid)
                    .bind("accessGroupUid", accessGroupUid)
                    .bind("modifyDt", DateHelper.formatDBDateTime(LocalDateTime.now()));
            insert = executeUpdate(update);
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return insert;

    }
}
