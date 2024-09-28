package id.tsi.mmw.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.tsi.mmw.model.Pagination;
import id.tsi.mmw.model.User;

import javax.ws.rs.core.Response;
import java.util.List;

public class UserResponse extends ServiceResponse{

    @JsonProperty("user")
    private User user;

    public UserResponse() {
        super(Response.Status.OK);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
