package sg.ic.umx.rest.model;

import java.util.List;
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.umx.model.Server;

public class ServerListResponse extends ServiceResponse {

    @JsonProperty("servers")
    private List<Server> serverList;

    public ServerListResponse() {
        super(Status.OK);
    }

    public List<Server> getServerList() {
        return serverList;
    }

    public void setServerList(List<Server> serverList) {
        this.serverList = serverList;
    }
}
