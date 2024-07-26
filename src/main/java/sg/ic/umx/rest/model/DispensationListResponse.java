package sg.ic.umx.rest.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import javax.ws.rs.core.Response.Status;
import sg.ic.umx.model.Dispensation;

public class DispensationListResponse extends ServiceResponse {

    @JsonProperty("dispensations")
    private List<Dispensation> dispensationList;

    public DispensationListResponse() {
        super(Status.OK);
    }

    public List<Dispensation> getDispensationList() {
        return dispensationList;
    }

    public void setDispensationList(List<Dispensation> dispensationList) {
        this.dispensationList = dispensationList;
    }


}
