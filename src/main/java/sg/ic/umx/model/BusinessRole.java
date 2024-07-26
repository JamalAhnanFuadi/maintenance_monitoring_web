package sg.ic.umx.model;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import sg.ic.umx.util.json.LocalDateTimeDeserializer;
import sg.ic.umx.util.json.LocalDateTimeSerializer;

public class BusinessRole {

    @JsonProperty("id")
    private String id;

    @JsonProperty("name")
    private String name;

    @JsonProperty("hrRole")
    private String hrRole;

    @JsonProperty("description")
    private String description;

    @JsonProperty("applicationRoles")
    private List<ApplicationRole> roleList;

    @JsonProperty("createdDt")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdDt;

    public BusinessRole() {
        roleList = new ArrayList<>();
    }

    public BusinessRole(String id, String name) {
        this();
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getHrRole() {
        return hrRole;
    }

    public void setHrRole(String hrRole) {
        this.hrRole = hrRole;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addRole(ApplicationRole role) {
        this.roleList.add(role);
    }

    public List<ApplicationRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<ApplicationRole> roleList) {
        this.roleList = roleList;
    }

    public LocalDateTime getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(LocalDateTime createdDt) {
        this.createdDt = createdDt;
    }

}
