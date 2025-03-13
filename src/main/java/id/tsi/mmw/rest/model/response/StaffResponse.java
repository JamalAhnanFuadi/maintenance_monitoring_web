package id.tsi.mmw.rest.model.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import id.tsi.mmw.model.Staff;

import javax.ws.rs.core.Response;

public class StaffResponse extends ServiceResponse{

    @JsonProperty("staff")
    private Staff staff;

    public StaffResponse() {
        super(Response.Status.OK);
    }

    public Staff getStaff() {
        return staff;
    }

    public void setStaff(Staff staff) {
        this.staff = staff;
    }
}
