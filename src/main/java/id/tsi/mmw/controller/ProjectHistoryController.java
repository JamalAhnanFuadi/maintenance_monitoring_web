package id.tsi.mmw.controller;

import id.tsi.mmw.model.*;
import id.tsi.mmw.util.helper.DateHelper;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectHistoryController extends BaseController {

    public ProjectHistoryController() {
        log = getLogger(this.getClass());
    }


    public List<ProjectHistory> getProjectHistory(String projectUid) {
        final String methodName = "getProjectList";
        start(methodName);
        List<ProjectHistory> result = new ArrayList<>();

        String sql = "SELECT uid, project_uid, projectName, actor, event, message_title, message_detail, icon, updateDate AS`date`, updateTime AS `time`, create_dt " +
                "FROM view_project_history " +
                "WHERE project_uid = :projectUid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("projectUid", projectUid);
            result = q.mapToBean(ProjectHistory.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean addProjectHistory(ProjectHistory projectHistory) {
        final String methodName = "addProjectHistory";
        start(methodName);
        boolean result = false;

        String sql = "INSERT INTO project_history " +
                "(uid, project_uid, actor, event, message_title, message_detail, icon, `date`, `time`, create_dt, request) " +
                "VALUES( LOWER(UUID()), :projectUid, :actor, :event, :messageTitle, :messageDetail, :icon, :date, :time, :createDt, :request);";

        try (Handle handle = getHandle(); Update u = handle.createUpdate(sql)) {

            u.bindBean(projectHistory);
            result = executeUpdate(u);

        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

}
