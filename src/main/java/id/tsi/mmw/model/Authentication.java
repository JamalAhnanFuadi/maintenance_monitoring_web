package id.tsi.mmw.model;

public class Authentication {

    private String uid;
    private String salt;
    private String password;
    private boolean loginAllowed;
    private String passwordLastSet;
    private String lastLoginDt;

    public Authentication() {
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isLoginAllowed() {
        return loginAllowed;
    }

    public void setLoginAllowed(boolean loginAllowed) {
        this.loginAllowed = loginAllowed;
    }

    public String getPasswordLastSet() {
        return passwordLastSet;
    }

    public void setPasswordLastSet(String passwordLastSet) {
        this.passwordLastSet = passwordLastSet;
    }

    public String getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }
}
