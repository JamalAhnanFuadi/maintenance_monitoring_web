package id.tsi.mmw.rest.service;

import id.tsi.mmw.controller.UserController;
import id.tsi.mmw.model.User;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

@Singleton
@Path("users")
@Produces(MediaType.APPLICATION_JSON)
public class UserService extends BaseService  {


@Inject
private UserController userController;
    public UserService() { log = getLogger(this.getClass()); }

    @GET
    @PermitAll
    public Response getUserList() {
        final String methodName = "getUserList";
        start(methodName);

        List<User> userList = userController.getUserList();
        completed(methodName);
        return buildSuccessResponse(userList);
    }
}
