package id.tsi.mmw.controller;

import id.tsi.mmw.model.UserAccessMatrix;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccessMatrixController extends BaseController{

    public AccessMatrixController() {
        log = getLogger(this.getClass());
    }

    /**
     * Retrieves the access matrix of a user in a particular access group.
     *
     * @param userUid The user ID to find the access matrix for
     * @param accessGroupUid The access group ID to find the access matrix for
     *
     * @return A list of {@link UserAccessMatrix} objects, each representing an access matrix entry
     *         for the given user in the given access group.
     */
    public List<UserAccessMatrix> getUserAccessMatrixRole(String userUid, String accessGroupUid) {
        final String methodName = "getUserList";
        start(methodName);
        List<UserAccessMatrix> result = new ArrayList<>();
        String sql = "SELECT a.code AS user_role, al.display_name AS role_access " +
                "FROM access_matrix am " +
                " LEFT JOIN access_group ag ON ag.uid =am.access_group_uid " +
                " LEFT JOIN application a ON a.uid = am.application_uid " +
                " LEFT JOIN access_level al ON al.uid = am.access_level_uid " +
                " WHERE am.user_uid = :userUid and am.access_group_uid = :accessGroupUid";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            /*
             * Bind the parameters to the query.
             * The query is using the following parameters:
             *  - :userUid: the user ID to find the access matrix for
             *  - :accessGroupUid: the access group ID to find the access matrix for
             */
            q.bind("userUid", userUid);
            q.bind("accessGroupUid", accessGroupUid);
            result = q.mapToBean(UserAccessMatrix.class).list();
        } catch (Exception e) {
            // Log any SQL exception that occurs during the authentication process
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    /**
     * Retrieves the access matrix of a user in a particular access group.
     *
     * @param userUid The user ID to find the access matrix for
     * @param accessGroupUid The access group ID to find the access matrix for
     *
     * @return A list of {@link UserAccessMatrix} objects, each representing an access matrix entry
     *         for the given user in the given access group.
     */
    public List<UserAccessMatrix> getUserAccessMatrix(String userUid, String accessGroupUid) {
        final String methodName = "getUserAccessMatrix";
        start(methodName);
        List<UserAccessMatrix> result = new ArrayList<>();
        String sql = "SELECT am.uid, a.display_name AS application, a.code AS application_code, al.display_name AS access_level, " +
                " u.fullname AS grant_by, am.expired_dt, am.modify_dt, am.create_dt " +
                " FROM access_matrix am " +
                " LEFT JOIN access_group ag ON ag.uid =am.access_group_uid " +
                " LEFT JOIN application a ON a.uid = am.application_uid " +
                " LEFT JOIN access_level al ON al.uid = am.access_level_uid " +
                " LEFT JOIN user u ON u.uid = am.granted_by " +
                " WHERE am.user_uid = :userUid and am.access_group_uid = :accessGroupUid";
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            /*
             * Bind the parameters to the query.
             * The query is using the following parameters:
             *  - :userUid: the user ID to find the access matrix for
             *  - :accessGroupUid: the access group ID to find the access matrix for
             */
            q.bind("userUid", userUid);
            q.bind("accessGroupUid", accessGroupUid);
            result = q.mapToBean(UserAccessMatrix.class).list();
        } catch (Exception e) {
            // Log any SQL exception that occurs during the authentication process
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

}
