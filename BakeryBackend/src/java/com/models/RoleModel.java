package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class RoleModel extends SuperModel {
    private int roleid;
    private String role_name;
    private String permissions;
    private String role_desc;
    public static final String sequence_id = "roles_roleid_seq";

    public RoleModel(int roleid, String role_name, String permissions,
            String role_desc) {
        this.roleid = roleid;
        this.role_name = role_name;
        this.permissions = permissions;
        this.role_desc = role_desc;
    }

    public RoleModel(String role_name, String permissions, String role_desc) {
        this.role_name = role_name;
        this.permissions = permissions;
        this.role_desc = role_desc;
    }

    public RoleModel(ResultSet set) throws Exception {
        this.roleid = set.getInt("roleid");
        this.role_name = set.getString("role_name");
        this.permissions = set.getString("permissions");
        this.role_desc = set.getString("role_desc");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObjectBuilder json = null;

        json = Json.createObjectBuilder()
                .add("roleid", roleid)
                .add("role_name", role_name)
                .add("permissions", permissions)
                .add("role_desc", role_desc);

        return json.build();
    }

    public RoleModel() {
    }

    public int getRoleid() {
        return roleid;
    }

    public void setRoleid(int roleid) {
        this.roleid = roleid;
    }

    public String getRole_name() {
        return role_name;
    }

    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }

    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }

    public String getRole_desc() {
        return role_desc;
    }

    public void setRole_desc(String role_desc) {
        this.role_desc = role_desc;
    }

    //debug database response
    @Override
    public String __response() {
        return ("roleid: " + roleid
                + " role_name: " + role_name
                + " permissions: " + permissions
                + " role_desc: " + role_desc);
    }
    
}
