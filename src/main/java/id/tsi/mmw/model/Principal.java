package id.tsi.mmw.model;

import java.io.Serializable;

public class Principal implements Serializable {

    private static final long serialVersionUID = 2264844452903044722L;

    private String username;

    public Principal() {}

    public Principal(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

}
