package sg.ic.umx.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.jdbi.v3.core.Handle;
import org.jdbi.v3.core.statement.PreparedBatch;
import org.jdbi.v3.core.statement.Query;
import org.jdbi.v3.core.statement.Update;
import sg.ic.umx.model.ServerConfiguration;

public class ServerConfigurationController extends BaseController {

    private static final String SERVER_ID = "serverId";

    public ServerConfigurationController() {
        log = getLogger(this.getClass());
    }


    public Map<String, List<String>> list() {
        final String methodName = "list";
        start(methodName);
        final String sql = "SELECT * FROM [server_configuration]";
        Map<String, List<String>> result = new HashMap<>();
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            List<ServerConfiguration> configList = q.mapToBean(ServerConfiguration.class).list();
            configList.forEach(config -> {
                String key = config.getServerId();
                if (!result.containsKey(key)) {
                    result.put(key, new ArrayList<>());
                }
                result.get(key).add(config.getConfigurationName());
            });

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public List<String> get(String serverId) {
        final String methodName = "get";
        start(methodName);
        final String sql = "SELECT configurationName FROM [server_configuration] WHERE serverId = :serverId";
        List<String> result = null;
        try (Handle h = getHandle(); Query q = h.createQuery(sql)) {
            result = q.bind(SERVER_ID, serverId).mapTo(String.class).list();
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;
    }

    public boolean delete(String serverId) {
        final String methodName = "delete";
        start(methodName);
        boolean result = false;
        final String sql = "DELETE FROM [server_configuration] WHERE serverId = :serverId";
        try (Handle h = getHandle(); Update u = h.createUpdate(sql)) {
            u.bind(SERVER_ID, serverId);
            result = executeUpdate(u);
        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;

    }

    public boolean create(String serverId, List<String> configurationList) {
        final String methodName = "create";
        start(methodName);
        boolean result = false;
        final String sql =
                "INSERT INTO [server_configuration] (serverId,configurationName) VALUES (:serverId,:configurationName)";
        try (Handle h = getHandle(); PreparedBatch batch = h.prepareBatch(sql)) {

            if (!configurationList.isEmpty()) {
                for (String configurationName : configurationList) {
                    batch.bind(SERVER_ID, serverId).bind("configurationName", configurationName).add();
                }
                result = executeBatch(batch);
            }

        } catch (Exception ex) {
            log.error(methodName, ex);
        }
        completed(methodName);
        return result;

    }
}
