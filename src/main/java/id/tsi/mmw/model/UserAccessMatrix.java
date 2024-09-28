package id.tsi.mmw.model;

public class UserAccessMatrix {

    private String userRole;
    private String roleAccess;

    public UserAccessMatrix() {
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getRoleAccess() {
        return roleAccess;
    }

    public void setRoleAccess(String roleAccess) {
        this.roleAccess = roleAccess;
    }
}
