package id.tsi.mmw.rest.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AuthenticationRequest {
    @JsonProperty("username")
    private String username;
    @JsonProperty("password")
    private String password;

    public AuthenticationRequest() {
        // Empty Constructor
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
