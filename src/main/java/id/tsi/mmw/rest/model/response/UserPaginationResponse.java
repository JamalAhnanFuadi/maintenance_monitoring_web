package id.tsi.mmw.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.tsi.mmw.model.Pagination;
import id.tsi.mmw.model.User;

import javax.ws.rs.core.Response;
import java.util.List;

public class UserPaginationResponse extends ServiceResponse{

    @JsonProperty("filter")
    private Pagination filter;

    @JsonProperty("users")
    private List<User> users;

    public UserPaginationResponse() {
        super(Response.Status.OK);
    }

    public Pagination getFilter() {
        return filter;
    }

    public void setFilter(Pagination filter) {
        this.filter = filter;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }
}
