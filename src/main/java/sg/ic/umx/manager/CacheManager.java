package sg.ic.umx.manager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import javax.inject.Singleton;
import sg.ic.umx.controller.ServerConfigurationController;
import sg.ic.umx.controller.ServerController;
import sg.ic.umx.engine.model.ApplicationAccount;
import sg.ic.umx.model.Server;

@Singleton
public class CacheManager {
    private static CacheManager instance;

    // Validity of 1 hour
    private long cacheValidity = 60 * 60L;

    private Map<Long, Long> userHashExpiryMap;
    private Map<Long, Map<String, String>> applicationUserHashMap;

    private Map<Long, Long> accountRoleExpiryMap;
    private Map<Long, Map<String, ApplicationAccount>> applicationAccountRoleMap;

    private Map<String, Server> serverMap;
    private final ServerController serverController;
    private final ServerConfigurationController serverConfigurationController;

    private CacheManager() {
        applicationUserHashMap = new HashMap<>();
        applicationAccountRoleMap = new HashMap<>();
        userHashExpiryMap = new HashMap<>();
        accountRoleExpiryMap = new HashMap<>();

        serverMap = new HashMap<>();
        serverController = new ServerController();
        serverConfigurationController = new ServerConfigurationController();
        refreshServerMap();
    }

    public void addUserHashMap(long applicationId, Map<String, String> userHashMap) {
        userHashExpiryMap.put(applicationId, System.currentTimeMillis());
        applicationUserHashMap.put(applicationId, userHashMap);
    }

    public void addAccountRoleMap(long applicationId, Map<String, ApplicationAccount> accountRoleMap) {
        accountRoleExpiryMap.put(applicationId, System.currentTimeMillis());
        applicationAccountRoleMap.put(applicationId, accountRoleMap);
    }

    public boolean isUserHashValid(long applicationId) {

        if (userHashExpiryMap.containsKey(applicationId)) {
            long now = System.currentTimeMillis();
            long entryTime = userHashExpiryMap.get(applicationId);

            return (now - entryTime) < cacheValidity;
        }

        return false;
    }

    public boolean isAccountRoleValid(long applicationId) {

        if (accountRoleExpiryMap.containsKey(applicationId)) {
            long now = System.currentTimeMillis();
            long entryTime = accountRoleExpiryMap.get(applicationId);

            return (now - entryTime) < cacheValidity;
        }

        return false;
    }

    public Map<String, String> getUserHashMap(long applicationId) {

        if (isUserHashValid(applicationId)) {
            return applicationUserHashMap.get(applicationId);
        }
        return null;
    }

    public Map<String, ApplicationAccount> getAccountRoleMap(long applicationId) {

        if (isAccountRoleValid(applicationId)) {
            return applicationAccountRoleMap.get(applicationId);
        }
        return null;
    }

    public void refreshServerMap() {
        serverMap.clear();
        List<Server> serverList = serverController.list();
        Map<String, List<String>> configMap = serverConfigurationController.list();

        serverMap = serverList.stream().collect(Collectors.toMap(Server::getId, server -> {
            if (configMap.containsKey(server.getId())) {
                server.setConfigurationList(configMap.get(server.getId()));
            } else {
                server.setConfigurationList(new ArrayList<>());
            }
            return server;
        }));
    }

    public Map<String, Server> getServerMap() {
        return serverMap;
    }

    public static CacheManager getInstance() {
        if (instance == null) {
            instance = new CacheManager();
        }
        return instance;
    }

}
