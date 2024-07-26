package sg.ic.umx.rest.model;

import java.util.List;
import javax.ws.rs.core.Response.Status;
import com.fasterxml.jackson.annotation.JsonProperty;
import sg.ic.umx.model.Application;

public class ApplicationListResponse extends ServiceResponse {

    @JsonProperty("applications")
    private List<Application> applicationList;

    public ApplicationListResponse() {
        super(Status.OK);
    }

    public ApplicationListResponse(List<Application> applicationList) {
        super(Status.OK);
        this.applicationList = applicationList;
    }

    public List<Application> getApplicationList() {
        return applicationList;
    }

    public void setApplicationList(List<Application> applicationList) {
        this.applicationList = applicationList;
    }

}
