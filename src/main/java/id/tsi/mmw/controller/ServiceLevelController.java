package id.tsi.mmw.controller;

import id.tsi.mmw.model.ServiceLevel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ServiceLevelController extends BaseController {

    public ServiceLevelController() {
        log = getLogger(this.getClass());
    }

    public List<ServiceLevel> getServiceLevelList() {
        final String methodName = "getServiceLevelList";
        start(methodName);
        List<ServiceLevel> result = new ArrayList<>();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt " +
                " FROM service_level d " +
                " ORDER BY d.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(ServiceLevel.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public ServiceLevel getServiceLevel(String uid) {
        final String methodName = "getServiceLevel";
        start(methodName);
        ServiceLevel result = new ServiceLevel();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt " +
                " FROM service_level d " +
                " WHERE d.uid = :uid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(ServiceLevel.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addServiceLevel(ServiceLevel ServiceLevel) {
        final String methodName = "addServiceLevel";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO service_level " +
                "(uid, display_name, description, create_dt) " +
                "VALUES( LOWER(UUID()), :displayName, :description, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(ServiceLevel);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateServiceLevel(ServiceLevel ServiceLevel) {
        final String methodName = "updateServiceLevel";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE service_level " +
                " SET display_name = :displayName, description = :description, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(ServiceLevel);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateServiceLevel(String uid) {
        final String methodName = "validateServiceLevel";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM service_level " +
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


    public boolean deleteServiceLevel(String uid) {
        final String methodName = "deleteServiceLevel";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM service_level WHERE uid = :uid";
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
