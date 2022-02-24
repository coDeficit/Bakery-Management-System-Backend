package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

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

    public RoleModel(long roleid, String name, String permissions,
            String description, Timestamp createdat, Timestamp updatedat) {
        this.roleid = roleid;
        this.name = name;
        this.permissions = permissions;
        this.description = description;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public RoleModel(String name, String permissions, String description) {
        this.name = name;
        this.permissions = permissions;
        this.description = description;
    }

    public RoleModel(ResultSet set) throws Exception {
        this.roleid = set.getLong("roleid");
        this.name = set.getString("name");
        this.permissions = set.getString("permissions");
        this.description = set.getString("description");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObjectBuilder json = null;

        json = Json.createObjectBuilder()
                .add("roleid", roleid)
                .add("name", name)
                .add("permissions", permissions)
                .add("description", description)
                .add("createdat", createdat.toString())
                .add("updatedat", updatedat.toString());

        return json.build();
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
