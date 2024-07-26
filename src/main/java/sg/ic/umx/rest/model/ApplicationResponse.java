/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sg.ic.umx.rest.model;

import javax.ws.rs.core.Response.Status;
import sg.ic.umx.model.Application;

/**
 *
 * @author permadi
 */
public class ApplicationResponse  extends ServiceResponse{
    
    private Application application;

    public ApplicationResponse() {
        super(Status.OK);
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
  
}
