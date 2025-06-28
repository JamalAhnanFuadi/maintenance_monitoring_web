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
public class ProjectController extends BaseController {

    public ProjectController() {
        log = getLogger(this.getClass());
    }

    public List<Project> getProjectList() {
        final String methodName = "getProjectList";
        start(methodName);
        List<Project> result = new ArrayList<>();

        String sql = "SELECT p.uid, p.display_name, c.display_name AS customerName, p.sales_order_number, p.job_code, " +
                "p.create_dt , p.modify_dt, p.mark_for_deletion, " +
                "    CONCAT( " +
                "        DATE_FORMAT(p.mark_for_deletion_dt, '%d %M %Y'), " +
                "        ' at ', " +
                "        DATE_FORMAT(p.mark_for_deletion_dt, '%h:%i %p') " +
                "    ) AS markForDeletionDt " +
                "FROM project p " +
                "JOIN customer c ON c.uid = p.customer_uid " +
                "ORDER BY p.display_name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            result = q.mapToBean(Project.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;
    }

    public List<ProjectTag> getProjectTagList(String projectUid) {
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

    public List<ProjectTag> getProjectTagListByServiceOrder(String projectUid, String serviceOrderUid) {
        final String methodName = "getProjectTagListByServiceOrder";
        List<ProjectTag> result = new ArrayList<>();

        String sql = "SELECT tag " +
                "FROM project_tag " +
                "WHERE project_uid = :projectUid AND service_order_uid = :serviceOrderUid " +
                "ORDER BY tag ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("projectUid", projectUid);
            q.bind("serviceOrderUid", serviceOrderUid);
            result = q.mapToBean(ProjectTag.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }
        return result;
    }

    public List<ProjectStaffPIC> getStaffPICList(String projectUid) {
        final String methodName = "getStaffPICList";
        List<ProjectStaffPIC> result = new ArrayList<>();

        String sql = "SELECT pp.uid, pp.staff_uid , CONCAT(s.firstname, ' ', s.lastname) AS staffName, s.email AS staffEmail " +
                "FROM project_pic pp " +
                "JOIN staff s ON s.uid = pp.staff_uid " +
                "WHERE pp.project_uid = :projectUid " +
                "ORDER BY staffName ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("projectUid", projectUid);
            result = q.mapToBean(ProjectStaffPIC.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }
        return result;
    }

    public List<ProjectCustomerPIC> getCustomerPICList(String projectUid) {
        final String methodName = "getCustomerPICList";
        List<ProjectCustomerPIC> result = new ArrayList<>();

        String sql = "SELECT pp.uid, pp.name, pp.phone, pp.email " +
                "FROM project_customer_pic pp " +
                "WHERE pp.project_uid = :projectUid " +
                "ORDER BY pp.name ASC;";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("projectUid", projectUid);
            result = q.mapToBean(ProjectCustomerPIC.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }
        return result;
    }

    public Project getProjectByUid(String projectUid) {
        final String methodName = "getProjectByUid";
        start(methodName);
        Project result = null;

        String sql = "SELECT p.uid, p.display_name, c.display_name AS customerName, p.customer_uid, p.sales_order_number, p.job_code, p.description, p.mark_for_deletion, p.create_dt , p.modify_dt, " +
                "    CONCAT( " +
                "        DATE_FORMAT(p.mark_for_deletion_dt, '%d %M %Y'), " +
                "        ' at ', " +
                "        DATE_FORMAT(p.mark_for_deletion_dt, '%h:%i %p') " +
                "    ) AS markForDeletionDt " +
                "FROM project p " +
                "JOIN customer c ON c.uid = p.customer_uid " +
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

    public List<ProjectServiceOrder> getServiceOrderList(String projectUid) {
        final String methodName = "getServiceOrderList";
        List<ProjectServiceOrder> result = new ArrayList<>();

        String sql = "SELECT ps.uid, ps.contract_number, ps.service_qty " +
                "FROM project_service_order ps " +
                "WHERE ps.project_uid = :projectUid " +
                "ORDER BY ps.contract_number ASC";

        try (Handle handle = getHandle(); Query q = handle.createQuery(sql)) {
            q.bind("projectUid", projectUid);
            result = q.mapToBean(ProjectServiceOrder.class).list();
        } catch (Exception e) {
            log.error(methodName, e);
        }
        return result;
    }

    public boolean addProject(Project project) {
        final String methodName = "addProject";
        start(methodName);
        boolean result = false;

        String projectSql = "INSERT INTO project " +
                "(uid, display_name, customer_uid, sales_order_number, job_code, description, create_dt) " +
                "VALUES (:uid, :displayName, :customerUid, :salesOrderNumber, :jobCode, :description, :createDt);";

        String staffPicSql = "INSERT INTO project_pic (uid, project_uid, staff_uid, create_dt) " +
                " VALUES ( LOWER(UUID()), :projectUid, :staffUid, :createDt);";

        try (Handle h = getHandle()) {
            result = h.inTransaction(handle -> {
                handle.begin();

                PreparedBatch projectInsertBatch = handle.prepareBatch(projectSql);
                projectInsertBatch.bindBean(project).add();
                boolean projectInsert = executeBatch(projectInsertBatch);

                if (!projectInsert) return false;

                PreparedBatch staffPicBatch = handle.prepareBatch(staffPicSql);
                for (ProjectStaffPIC staffPIC : project.getStaffPic()) {
                    staffPicBatch.bindBean(staffPIC).add();
                }

                boolean staffPicInsert = executeBatch(staffPicBatch);

                return projectInsert && staffPicInsert;
            });
        } catch (SQLException | IllegalArgumentException e) {
            log.error(methodName, e);
        }

        completed(methodName);
        return result;

    }

    public boolean markForDeletion(String uid) {
        final String methodName = "markForDeletion";
        start(methodName);
        boolean result = false;
        final String sql = "UPDATE project SET " +
                "mark_for_deletion = 1, project.mark_for_deletion_dt = :markForDeletionDt, modify_dt = :modifyDt " +
                "WHERE uid = :uid";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {

            LocalDateTime modifyLdt = LocalDateTime.now();
            String modifyDt = DateHelper.formatDBDateTime(modifyLdt);

            LocalDateTime markForDeletionLdt = modifyLdt.plusDays(30);
            String markForDeletionDt = DateHelper.formatDBDateTime(markForDeletionLdt);

            u.bind("uid", uid);
            u.bind("modifyDt", modifyDt);
            u.bind("markForDeletionDt", markForDeletionDt);

            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean permanentDeleteProduct(String uid) {
        final String methodName = "permanentDeleteProduct";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM project WHERE uid = :uid";
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
