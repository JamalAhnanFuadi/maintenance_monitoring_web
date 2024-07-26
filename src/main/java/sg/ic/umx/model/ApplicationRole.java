package sg.ic.umx.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationRole {

    @JsonIgnore
    private String id;

    @JsonProperty("application")
    private String application;

    @JsonProperty("role")
    private String role;

    public ApplicationRole() {
        // Empty Constructor
    }

    public ApplicationRole(String application, String role) {
        this.application = application;
        this.role = role;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }



}
