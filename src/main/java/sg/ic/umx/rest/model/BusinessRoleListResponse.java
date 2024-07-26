package sg.ic.umx.rest.model;

import java.util.List;
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.umx.model.BusinessRole;

public class BusinessRoleListResponse extends ServiceResponse {

    @JsonProperty("roles")
    private List<BusinessRole> roleList;

    public BusinessRoleListResponse() {
        super(Status.OK);
    }

    public BusinessRoleListResponse(List<BusinessRole> applicationList) {
        super(Status.OK);
        this.roleList = applicationList;
    }

    public List<BusinessRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<BusinessRole> roleList) {
        this.roleList = roleList;
    }
}
