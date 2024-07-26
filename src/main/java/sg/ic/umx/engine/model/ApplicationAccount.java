package sg.ic.umx.engine.model;

import java.util.HashSet;
import java.util.Set;

public class ApplicationAccount {

    private String accountId;
    private String userId;
    private Set<String> roleSet;

    public ApplicationAccount() {
        roleSet = new HashSet<>();
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public void setRoleSet(Set<String> roleSet) {
        this.roleSet = roleSet;
    }

    public void addRole(String role) {
        roleSet.add(role);
    }

}
