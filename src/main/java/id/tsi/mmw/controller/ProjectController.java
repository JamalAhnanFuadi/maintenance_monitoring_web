package id.tsi.mmw.controller;

import id.tsi.mmw.model.Project;
import id.tsi.mmw.model.ProjectTag;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;

import java.util.ArrayList;
import java.util.List;

@Controller
public class ProjectController extends BaseController {

    public ProjectController() {
        log = getLogger(this.getClass());
    }

    public List<Project> getProjectList() {
        final String methodName = "getProjectList";
        start(methodName);
        List<Project> result = new ArrayList<>();

        String sql = "SELECT p.uid, p.display_name, c.display_name AS customerName, p.sales_order_number, p.job_code, " +
                "CONCAT(s.firstname, ' ', s.lastname) AS staffName, p.create_dt , p.modify_dt " +
                "FROM project p " +
                "JOIN customer c ON c.uid = p.customer_uid " +
                "JOIN staff s ON s.uid = p.staff_uid " +
                "ORDER BY p.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(Project.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public List <ProjectTag> getProjectTagList( String projectUid ) {
        final String methodName = "getProjectList";
        List<ProjectTag> result = new ArrayList<>();

        String sql = "SELECT tag " +
                "FROM project_tag " +
                "WHERE project_uid = :projectUid " +
                "ORDER BY tag ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("projectUid", projectUid);
            result = q.mapToBean(ProjectTag.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }
        return result;
    }

    public Project getProjectByUid(String projectUid) {
        final String methodName = "getProjectByUid";
        start(methodName);
        Project result = null;

        String sql = "SELECT p.uid, p.display_name, c.display_name AS customerName, p.sales_order_number, p.job_code, " +
                "CONCAT(s.firstname, ' ', s.lastname) AS staffName, p.create_dt , p.modify_dt " +
                "FROM project p " +
                "JOIN customer c ON c.uid = p.customer_uid " +
                "JOIN staff s ON s.uid = p.staff_uid " +
                "WHERE p.uid = :projectUid;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("projectUid", projectUid);
            result = q.mapToBean(Project.class).first();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public boolean validateProject(String uid) {
        final String methodName = "validateProject";
        start(methodName);
        boolean result = false;
        String sql = "SELECT if(COUNT(*)>0,'true','false') " +
                " FROM project " +
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
}
