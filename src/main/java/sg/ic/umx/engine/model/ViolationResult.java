package sg.ic.umx.engine.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ViolationResult {

    private boolean hasViolated;
    private List<String> roleViolationList;

    public ViolationResult() {
        hasViolated = false;
        roleViolationList = new ArrayList<>();
    }

    public void addRole(String role) {
        this.hasViolated = true;
        this.roleViolationList.add(role);
    }

    public void addRole(Collection<? extends String> roleCollection) {
        this.hasViolated = true;
        this.roleViolationList.addAll(roleCollection);
    }

    public boolean isHasViolated() {
        return hasViolated;
    }

    public List<String> getRoleViolationList() {
        return roleViolationList;
    }
}
