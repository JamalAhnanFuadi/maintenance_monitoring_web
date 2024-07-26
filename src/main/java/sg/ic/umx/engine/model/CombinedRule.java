package sg.ic.umx.engine.model;

import java.util.HashSet;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;

public class CombinedRule {

    private SortedMap<String, String> attributeMap;
    private Set<String> roleSet;

    public CombinedRule() {
        attributeMap = new TreeMap<>();
        roleSet = new HashSet<>();
    }

    public SortedMap<String, String> getAttributeMap() {
        return attributeMap;
    }

    public Set<String> getRoleSet() {
        return roleSet;
    }

    public void setAttributeMap(SortedMap<String, String> attributeMap) {
        this.attributeMap = attributeMap;
    }

    public void setRoleSet(Set<String> roleSet) {
        this.roleSet = roleSet;
    }

    public void addRole(String role) {
        this.roleSet.add(role);
    }

}
