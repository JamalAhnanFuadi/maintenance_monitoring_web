package id.tsi.mmw.controller;

import id.tsi.mmw.model.AccessGroup;
import id.tsi.mmw.model.Application;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.ArrayList;
import java.util.List;

@Controller
public class AccessGroupController extends BaseController{

    public AccessGroupController() {
        log = getLogger(this.getClass());
    }

    public List<AccessGroup> getAccessGroupList() {
        final String methodName = "getApplicationList";
        start(methodName);
        List<AccessGroup> result = new ArrayList<>();

        String sql = "SELECT uid, display_name, description, create_dt, modify_dt " +
                " FROM access_group " +
                " ORDER BY display_name ASC;";

        log.debug(methodName, "SQL : " + sql);
        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(AccessGroup.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }
}
