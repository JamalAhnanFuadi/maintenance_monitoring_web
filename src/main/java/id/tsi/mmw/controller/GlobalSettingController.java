package id.tsi.mmw.controller;

import id.tsi.mmw.model.Department;
import id.tsi.mmw.model.GlobalSetting;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Controller
public class GlobalSettingController extends BaseController {

    public GlobalSettingController() {
        log = getLogger(this.getClass());
    }

    public List<GlobalSetting> getGlobalSettings() {
        final String methodName = "getGlobalSettings";
        start(methodName);
        List<GlobalSetting> result = new ArrayList<>();

        String sql = "SELECT key, value, description, modify_dt " +
                " FROM global_setting " +
                " ORDER BY key ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(GlobalSetting.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

}
