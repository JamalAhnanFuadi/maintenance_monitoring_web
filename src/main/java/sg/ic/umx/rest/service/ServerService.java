package sg.ic.umx.rest.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import sg.ic.umx.controller.ApplicationController;
import sg.ic.umx.controller.IDGController;
import sg.ic.umx.controller.ServerConfigurationController;
import sg.ic.umx.controller.ServerController;
import sg.ic.umx.manager.CacheManager;
import sg.ic.umx.model.Application;
import sg.ic.umx.model.Server;
import sg.ic.umx.rest.model.ServerListResponse;
import sg.ic.umx.util.helper.StringHelper;

@Singleton
@PermitAll
@Path("servers")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ServerService extends BaseService {

    private final Executor executor;
    private final ServerController serverController;
    private final ServerConfigurationController serverConfigurationController;
    private final ApplicationController applicationController;
    private final IDGController idgController;

    public ServerService() {

        executor = initThreadPool();
        log = getLogger(this.getClass());
        serverController = new ServerController();
        applicationController = new ApplicationController();
        serverConfigurationController = new ServerConfigurationController();
        idgController = new IDGController();
    }

    @GET
    public Response list() {
        final String methodName = "list";
        start(methodName);
        ServerListResponse response = new ServerListResponse();
        response.setServerList(new ArrayList<>(CacheManager.getInstance().getServerMap().values()));
        completed(methodName);
        return buildSuccessResponse(response);
    }

    @POST
    public Response create(Server server) {
        final String methodName = "create";
        Response response = null;
        start(methodName);

        if (!validateServer(server, true)) {
            log.info(methodName, INVALID_REQUEST);
            response = buildInvalidRequestResponse();

        } else if (serverController.getCountByName(server.getName()) > 0) { // Check if Application already exists
            String errorMessage = "Resource Already Exists";
            log.info(methodName, errorMessage);
            response = getConflictedResponse(errorMessage);
        } else {

            // Generate Server ID
            server.setId(generateUUID());

            boolean result = serverController.create(server);

            // Refresh Cache
            refreshCache();

            executor.execute(() -> {
                updateConfigurations(server);

                refreshCache();
            });

            response = result ? buildSuccessResponse() : getErrorResponse();
        }
        completed(methodName);
        return response;
    }

    @PUT
    @Path("{serverId}")
    public Response update(@PathParam("serverId") String serverId, Server server) {
        final String methodName = "update";
        Response response = buildInvalidRequestResponse();
        start(methodName);

        if (!validateServer(server, false) || !serverExists(serverId)) {
            log.info(methodName, INVALID_REQUEST);
        } else {

            Server currServer = getServerFromCache(serverId);

            // Use current password if password field is empty
            if (!StringHelper.validate(server.getPassword())) {
                server.setPassword(currServer.getPassword());
            }

            server.setId(serverId);
            boolean result = serverController.update(server);

            refreshCache();

            executor.execute(() -> {
                updateConfigurations(server);

                // Refresh Cache
                refreshCache();

            });

            response = result ? buildSuccessResponse() : getErrorResponse();
        }
        completed(methodName);
        return response;
    }

    @GET
    @Path("{serverId}/configurations/refresh")
    public Response refreshConfigurations(@PathParam("serverId") String serverId) {
        final String methodName = "refreshConfigurations";
        Response response = buildInvalidRequestResponse();
        start(methodName);

        if (!serverExists(serverId)) {
            log.info(methodName, INVALID_REQUEST);
        } else {
            response = updateConfigurations(getServerFromCache(serverId)) ? buildSuccessResponse() : getErrorResponse();
            refreshCache();
        }
        completed(methodName);
        return response;
    }

    @DELETE
    @Path("{serverId}")
    public Response delete(@PathParam("serverId") String serverId) {
        final String methodName = "delete";
        Response response;
        start(methodName);

        // Check if Server is still in use
        List<Application> appList = applicationController.listByServerId(serverId);

        if (appList.isEmpty()) {
            boolean result = serverController.delete(serverId);

            serverConfigurationController.delete(serverId);

            refreshCache();

            response = result ? buildSuccessResponse() : getErrorResponse();
        } else {
            response = getConflictedResponse("Server still in use by applications");
        }

        completed(methodName);
        return response;
    }

    private boolean validateServer(Server server, boolean requirePassword) {
        boolean result = server != null && StringHelper.validate(server.getName())
                && StringHelper.validate(server.getUrl()) && StringHelper.validate(server.getUsername());

        if (requirePassword) {
            result = result && StringHelper.validate(server.getPassword());
        }

        return result;
    }

    private boolean updateConfigurations(Server server) {
        final String methodName = "updateConfigurations";
        start(methodName);
        boolean result = false;
        List<String> configList = idgController.getConfigurations(server);


        // Delete old Configurations
        serverConfigurationController.delete(server.getId());

        if (!configList.isEmpty()) {
            result = serverConfigurationController.create(server.getId(), configList);
        }

        // Get Applications by Server ID
        List<Application> appList = applicationController.listByServerId(server.getId());
        List<Long> validAppList = new ArrayList<>();
        List<Long> invalidAppList = new ArrayList<>();
        Set<String> configSet = new HashSet<>(configList);

        for (Application app : appList) {
            if (configSet.contains(app.getConfigurationName())) {
                validAppList.add(app.getId());
            } else {
                invalidAppList.add(app.getId());
            }
        }
        // Update Application Status based on updated configurations
        applicationController.updateApplicationStatus(validAppList, true);
        applicationController.updateApplicationStatus(invalidAppList, false);

        completed(methodName);
        return result;
    }

    private boolean serverExists(String id) {
        return CacheManager.getInstance().getServerMap().containsKey(id);
    }

    private Server getServerFromCache(String id) {
        if (serverExists(id)) {
            return CacheManager.getInstance().getServerMap().get(id);
        }
        return new Server();
    }

    private void refreshCache() {
        CacheManager.getInstance().refreshServerMap();
    }
}
