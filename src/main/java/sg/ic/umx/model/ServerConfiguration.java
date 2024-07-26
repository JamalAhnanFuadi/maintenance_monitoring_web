package sg.ic.umx.model;

public class ServerConfiguration {

    private String serverId;

    private String configurationName;

    public ServerConfiguration() {
        // Empty Constructor
    }

    public String getServerId() {
        return serverId;
    }

    public void setServerId(String serverId) {
        this.serverId = serverId;
    }

    public String getConfigurationName() {
        return configurationName;
    }

    public void setConfigurationName(String configurationName) {
        this.configurationName = configurationName;
    }
}
