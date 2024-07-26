package sg.ic.umx.idg.model;

import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RoleListResponse {

    @JsonProperty("array")
    private List<ItemPair> roleList;

    public RoleListResponse() {
        this.roleList = new ArrayList<>();
    }

    public List<ItemPair> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<ItemPair> roleList) {
        this.roleList = roleList;
    }
}
