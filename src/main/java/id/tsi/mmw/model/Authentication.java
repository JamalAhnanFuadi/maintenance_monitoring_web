package id.tsi.mmw.model;

public class Authentication {

    private String uid;
    private String salt;
    private String passwordHash;
    private boolean loginAllowed;
    private String createDt;
    private String lastPasswordSet;
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

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isLoginAllowed() {
        return loginAllowed;
    }

    public void setLoginAllowed(boolean loginAllowed) {
        this.loginAllowed = loginAllowed;
    }

    public String getCreateDt() {
        return createDt;
    }

    public void setCreateDt(String createDt) {
        this.createDt = createDt;
    }

    public String getLastPasswordSet() {
        return lastPasswordSet;
    }

    public void setLastPasswordSet(String lastPasswordSet) {
        this.lastPasswordSet = lastPasswordSet;
    }

    public String getLastLoginDt() {
        return lastLoginDt;
    }

    public void setLastLoginDt(String lastLoginDt) {
        this.lastLoginDt = lastLoginDt;
    }
}
