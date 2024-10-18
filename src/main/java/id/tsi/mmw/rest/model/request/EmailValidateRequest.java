package id.tsi.mmw.rest.model.request;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EmailValidateRequest {

    @JsonProperty("email")
    private String email;

    public EmailValidateRequest() {
        // default constructor
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
