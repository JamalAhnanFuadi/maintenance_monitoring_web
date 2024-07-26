package sg.ic.umx.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.model.Execution;
import sg.ic.umx.model.ExecutionStatus;
import sg.ic.umx.util.helper.DateHelper;

public class ExecutionController extends BaseController {

    private static final String STATUS = "status";
    private static final String APPLICATION_ID = "applicationId";
    private static final String ID = "id";


    public ExecutionController() {
        this.log = getLogger(this.getClass());
    }

    public boolean create(Execution execution) {
        final String methodName = "create";
        final String sql =
                "INSERT INTO [execution] (id, applicationId, name, status,startDt) VALUES (:id,:applicationId,:name,:status,:startDt);";
        boolean result = false;

        start(methodName);
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {

            u.bindBean(execution);
            result = executeUpdate(u);

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean updateStatus(String id, String status) {
        final String methodName = "updateStatus";
        final String sql = "UPDATE [execution] SET status = :status WHERE id = :id;";
        boolean result = false;
        start(methodName);
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {

            u.bind(STATUS, status).bind(ID, id);
            result = executeUpdate(u);

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean update(Execution execution) {
        final String methodName = "update";
        final String sql = "UPDATE [execution] SET status = :status, completedDt = :completedDt WHERE id = :id;";
        boolean result = false;
        start(methodName);
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {

            u.bindBean(execution);
            result = executeUpdate(u);

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean delete(String executionId) {
        final String methodName = "delete";
        final String sql = "DELETE FROM [execution] WHERE id = :id;";
        boolean result = false;
        start(methodName);
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind(ID, executionId);
            result = executeUpdate(u);

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public Execution get(String executionId) {
        final String methodName = "get";
        final String sql = "SELECT * FROM [execution] WHERE id = :id;";
        Optional<Execution> result = Optional.empty();
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(ID, executionId).mapToBean(Execution.class).findFirst();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result.isPresent() ? result.get() : null;
    }

    public List<Execution> listByApplicationId(long applicationId) {
        final String methodName = "listByApplicationId";
        final String sql = "SELECT * FROM [execution] where applicationId = :applicationId;";
        List<Execution> result = new ArrayList<>();
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(APPLICATION_ID, applicationId).mapToBean(Execution.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<Execution> listByApplicationId(long applicationId, ExecutionStatus status) {
        final String methodName = "listByApplicationId";
        final String sql = "SELECT * FROM [execution] where applicationId = :applicationId AND status =:status;";
        List<Execution> result = new ArrayList<>();
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(APPLICATION_ID, applicationId).bind(STATUS, status).mapToBean(Execution.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public Execution getLatestExecutionByApplicationId(long applicationId, ExecutionStatus status) {
        final String methodName = "getLatestExecutionByApplicationId";
        final String sql =
                "SELECT * FROM [execution] where applicationId = :applicationId AND status =:status ORDER BY completedDt DESC;";
        Optional<Execution> result = Optional.empty();
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(APPLICATION_ID, applicationId).bind(STATUS, status).mapToBean(Execution.class).findFirst();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result.isPresent() ? result.get() : null;
    }

    public List<Execution> listByApplicationIdAndDate(long applicationId, LocalDateTime startDt, LocalDateTime completedDt) {
        final String methodName = "listByApplicationIdAndDate";
        final String sql =
                "SELECT * FROM [execution] WHERE applicationId = :applicationId AND startDt BETWEEN :startDt AND :completedDt;";
        List<Execution> result = new ArrayList<>();
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(APPLICATION_ID, applicationId).bind("startDt", DateHelper.formatDateTime(startDt))
                    .bind("completedDt", DateHelper.formatDateTime(completedDt)).mapToBean(Execution.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }
}
