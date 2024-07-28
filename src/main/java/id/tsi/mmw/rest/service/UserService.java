package id.tsi.mmw.rest.service;

import javax.annotation.security.PermitAll;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Singleton
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService extends BaseService  {

    public UserService() {
        log = getLogger(this.getClass());
    }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        completed(methodName);
        return buildSuccessResponse("hello jamal");
    }
}
