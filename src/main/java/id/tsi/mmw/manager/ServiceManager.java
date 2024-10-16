package id.tsi.mmw.manager;

import javax.ws.rs.ApplicationPath;

import org.glassfish.jersey.media.multipart.MultiPartFeature;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.ServerProperties;

@ApplicationPath("/rest")
public class ServiceManager extends ResourceConfig {

    public ServiceManager() {
        packages("id.tsi.mmw.rest").register(MultiPartFeature.class);
        property(ServerProperties.BV_SEND_ERROR_IN_RESPONSE, true);
    }

}
