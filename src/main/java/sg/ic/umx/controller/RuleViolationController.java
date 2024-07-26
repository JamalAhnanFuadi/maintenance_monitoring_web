package sg.ic.umx.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.engine.model.RuleViolation;

public class RuleViolationController extends BaseController {

    private static final String EXECUTION_ID = "executionId";

    public RuleViolationController() {
        log = getLogger(this.getClass());
    }

    public boolean create(List<RuleViolation> violationList) {
        final String methodName = "create";
        start(methodName);
        boolean result = false;
        final String sql = "INSERT INTO [execution_violation] (executionId,accountId,accountType,userId,roleList) "
                + "VALUES (:executionId,:accountId,:accountType,:userId,:roleList);";

        try (Handle h = getHandle(); PreparedBatch batch = h.prepareBatch(sql)) {
            if (!violationList.isEmpty()) {
                for (RuleViolation violation : violationList) {
                    batch.bindBean(violation).add();
                }
                result = executeBatch(batch);
            } else {
                // No records to Insert
                result = true;
            }

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean deleteByExecutionId(String executionId) {
        final String methodName = "deleteByExecutionId";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM [execution_violation] WHERE executionId = :executionId;";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind(EXECUTION_ID, executionId);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<RuleViolation> listByExecutionId(String executionId) {
        final String methodName = "listByExecutionId";
        start(methodName);
        List<RuleViolation> result = new ArrayList<>();
        final String sql = "SELECT * FROM [execution_violation] WHERE executionId = :executionId";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {

            result = q.bind(EXECUTION_ID, executionId).mapToBean(RuleViolation.class).list();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public RuleViolation get(String executionId) {
        final String methodName = "get";
        final String sql = "SELECT * FROM [execution_violation] WHERE executionId = :executionId;";
        Optional<RuleViolation> result = Optional.empty();
        start(methodName);
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(EXECUTION_ID, executionId).mapToBean(RuleViolation.class).findFirst();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result.isPresent() ? result.get() : null;
    }

}
