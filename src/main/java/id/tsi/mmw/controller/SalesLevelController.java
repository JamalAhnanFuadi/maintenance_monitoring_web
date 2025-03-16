package id.tsi.mmw.controller;

import id.tsi.mmw.model.SalesLevel;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class SalesLevelController extends BaseController {

    public SalesLevelController() {
        log = getLogger(this.getClass());
    }

    public List<SalesLevel> getSalesLevelList() {
        final String methodName = "getSalesLevelList";
        start(methodName);
        List<SalesLevel> result = new ArrayList<>();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt " +
                " FROM sales_level d " +
                " ORDER BY d.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(SalesLevel.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public SalesLevel getSalesLevel(String uid) {
        final String methodName = "getSalesLevel";
        start(methodName);
        SalesLevel result = new SalesLevel();

        String sql = "SELECT d.uid, d.display_name, d.description, d.create_dt, d.modify_dt " +
                " FROM sales_level d " +
                " WHERE d.uid = :uid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("uid", uid);
            result = q.mapToBean(SalesLevel.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addSalesLevel(SalesLevel salesLevel) {
        final String methodName = "addSalesLevel";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO sales_level " +
                "(uid, display_name, description, create_dt) " +
                "VALUES( LOWER(UUID()), :displayName, :description, CURRENT_TIMESTAMP);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(salesLevel);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean updateSalesLevel(SalesLevel salesLevel) {
        final String methodName = "updateSalesLevel";
        start(methodName);
        boolean result = false;

        String sql = "UPDATE sales_level " +
                " SET display_name = :displayName, description = :description, modify_dt = CURRENT_TIMESTAMP " +
                " WHERE uid = :uid;";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {
            u.bindBean(salesLevel);
            result = executeUpdate(u);

        } catch (SQLException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean validateSalesLevel(String uid) {
        final String methodName = "validateSalesLevel";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM sales_level " +
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


    public boolean deleteSalesLevel(String uid) {
        final String methodName = "deleteSalesLevel";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM sales_level WHERE uid = :uid";
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
