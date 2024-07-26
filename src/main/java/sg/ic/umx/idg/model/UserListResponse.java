package sg.ic.umx.idg.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserListResponse {
    @JsonProperty("array")
    private List<IDGUser> userList;

    public UserListResponse() {
        this.userList = new ArrayList<>();
    }

    public List<IDGUser> getUserList() {
        return userList;
    }

    public void setUserList(List<IDGUser> userList) {
        this.userList = userList;
    }
}
