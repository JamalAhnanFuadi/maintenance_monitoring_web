package id.tsi.mmw.model;

import com.fasterxml.jackson.annotation.JsonProperty;
public class AccessMatric {

    @JsonProperty("access_id")
    private String accessId;
    @JsonProperty("user_id")
    private String userId;
    @JsonProperty("resource_id")
    private String resourceId;
    @JsonProperty("access_level")
    private String accessLevel;
    @JsonProperty("granted_at")
    private String grantedAt;
    @JsonProperty("revoked_at")
    private String revokedAt;

    public String getAccessId(){return accessId;}
    public void setAccessId(String accessId){this.accessId = accessId;}
    public String getUserId(){return userId;}
    public void setUserId(String userId){this.userId = userId;}
    public String getResourceId(){return resourceId;}
    public void setResourceId(String resourceId){this.resourceId = resourceId;}
    public String getAccessLevel(){return accessLevel;}
    public void setAccessLevel(String accessLevel){this.accessLevel = accessLevel;}
    public String getGrantedAt(){return grantedAt;}
    public void setGrantedAt(String grantedAt){this.grantedAt = grantedAt;}
    public String getRevokedAt(){return revokedAt;}
    public void setRevokedAt(String revokedAt){this.revokedAt = revokedAt;}
}
