
package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;


public class RoleModel extends SuperModel {

    private long roleid;
    private String name;
    private String permissions;
    private String description;
    private long createdby;
    private long updatedby;
    private Timestamp createdat;
    private Timestamp updatedat;
    public static final String sequence_id = "roles_roleid_seq";

    public RoleModel( long roleid, String name, String permissions,  
    String description, long createdby, long updatedby, Timestamp createdat, Timestamp updatedat ) {
        this.roleid = roleid;
        this.name = name;
        this.permissions = permissions;
        this.description = description;
        this.createdby = createdby;
        this.updatedby = updatedby;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public RoleModel(String name, String permissions, String description, long createdby, long updatedby ) {
        this.name = name;
        this.permissions = permissions;
        this.description = description;
        this.createdby = createdby;
        this.updatedby = updatedby;
    }

    public RoleModel(ResultSet set) throws Exception {
        this.roleid = set.getLong("roleid");
        this.name = set.getString("name");
        this.permissions = set.getString("permissions");
        this.description = set.getString("description");
        this.createdby = set.getLong("createdby");
        this.updatedby = set.getLong("updatedby");
        this.createdat = set.getTimestamp("createdat");
        this.updatedat = set.getTimestamp("updatedat");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject json = null;

        json = Json.createObjectBuilder()
                .add("roleid", roleid)
                .add("name", name)
                .add("permissions", permissions)
                .add("description", description)
                .add("createdby", createdby)
                .add("updatedby", updatedby)
                .add("createdat", createdat.toString())
                .add("updatedat", updatedat.toString())
                .build();

        return json;
    }

    public RoleModel() {
    }

    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getCreatedby() {
        return createdby;
    }

    public void setCreatedby(long createdby) {
        this.createdby = createdby;
    }

    public long getUpdatedby() {
        return updatedby;
    }

    public void setUpdatedby(long updatedby) {
        this.updatedby = updatedby;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Timestamp getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }
    
    
    
}
