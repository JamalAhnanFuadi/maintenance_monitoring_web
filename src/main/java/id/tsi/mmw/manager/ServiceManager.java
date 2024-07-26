package id.tsi.mmw.manager;

import javax.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/api")
public class ServiceManager extends ResourceConfig {

    public ServiceManager() {
        packages("id.tsi.mmw.rest");
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

}
