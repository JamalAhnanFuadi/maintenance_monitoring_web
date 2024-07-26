package sg.ic.umx.controller;

import java.util.Optional;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.engine.model.ExecutionData;

public class ExecutionDataController extends BaseController {

    public ExecutionDataController() {
        log = getLogger(this.getClass());
    }

    public boolean create(ExecutionData stat) {

        final String methodName = "create";
        start(methodName);
        boolean result = false;
        final String sql =
                "INSERT INTO [execution_data] (executionId,whitelistCompliant,whitelistNonCompliant,whitelistProcessed,normalCompliant,normalNonCompliant,normalProcessed,rulesProcessed,accountsProcessed) "
                        + "VALUES (:executionId,:whitelistCompliant,:whitelistNonCompliant, :whitelistProcessed, :normalCompliant,:normalNonCompliant,:normalProcessed,:rulesProcessed,:accountsProcessed);";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bindBean(stat);
            result = executeUpdate(u);
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
        final String sql = "DELETE FROM [execution_data] WHERE executionId = :executionId;";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind("executionId", executionId);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public ExecutionData getByExecutionId(String executionId) {
        final String methodName = "getByExecutionId";
        start(methodName);
        Optional<ExecutionData> result = Optional.empty();
        final String sql = "SELECT * FROM [execution_data] WHERE executionId = :executionId";

        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {

            result = q.bind("executionId", executionId).mapToBean(ExecutionData.class).findFirst();

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result.isPresent() ? result.get() : null;

    }
}
