package com.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class UserModel extends SuperModel {

    private UserModel creatorModel = null;
    private UserModel modifierModel = null;
    private EmployeeModel employeeModel = null;
    private RoleModel roleModel = null;
    private long userid;
    private long employeeid;
    private long roleid;
    private String username;
    private String password;
    private boolean state;
    private long createdby;
    private long updatedby;
    private Timestamp createdat;
    private Timestamp updatedat;
    public static final String sequence_id = "users_userid_seq";

    public UserModel(long employeeid, long roleid, String username, String password, boolean state, long createdby, long updatedby, Timestamp createdat, Timestamp updatedat) {
        this.employeeid = employeeid;
        this.roleid = roleid;
        this.username = username;
        this.password = password;
        this.state = state;
        this.createdby = createdby;
        this.updatedby = updatedby;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public UserModel(ResultSet set) throws SQLException, Exception {
        this.userid = set.getLong("userid");
        this.employeeid = set.getLong("employeeid");
        this.roleid = set.getLong("roleid");
        this.username = set.getString("username");
        this.password = set.getString("password");
        this.state = set.getBoolean("state");
        this.createdby = set.getLong("createdby");
        this.updatedby = set.getLong("updatedby");
        this.createdat = set.getTimestamp("createdat");
        this.updatedat = set.getTimestamp("updatedat");
         this.employeeModel = new EmployeeModel(set, false, false, false, false);
         this.roleModel = new RoleModel(set, false, false);
         this.creatorModel = new UserModel(set, false, false, false, false);
        this.modifierModel = new UserModel(set, false, false, false, false);
    }

    public UserModel(ResultSet set, boolean withEmp, boolean withRole, boolean withCreator, boolean withModifier) throws SQLException, Exception {
        this.userid = set.getLong("userid");
        this.employeeid = set.getLong("employeeid");
        this.roleid = set.getLong("roleid");
        this.username = set.getString("username");
        this.password = set.getString("password");
        this.state = set.getBoolean("state");
        this.createdby = set.getLong("createdby");
        this.updatedby = set.getLong("updatedby");
        this.createdat = set.getTimestamp("createdat");
        this.updatedat = set.getTimestamp("updatedat");

        if (withEmp) {
            this.employeeModel = new EmployeeModel(set, true, true, true, true);
        }
        if (withRole) {
            this.roleModel = new RoleModel(set, true, true);
        }
        if (withCreator) {
            this.creatorModel = new UserModel(set, true, true, true, true);
        }
        if (withModifier) {
            this.modifierModel = new UserModel(set, true, true, true, true);
        }
    }

    public UserModel() {
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObjectBuilder json = null;

        json = Json.createObjectBuilder()
                .add("userid", userid)
                .add("employeeid", employeeid)
                .add("roleid", roleid)
                .add("username", username)
                .add("password", password)
                .add("state", state)
                .add("createdby", createdby)
                .add("updatedby", updatedby)
                .add("createdat", createdat.toString())
                .add("updatedat", updatedat.toString());

        if (employeeModel != null) {
            json.add("employee_details", employeeModel.getJsonObject());
        }
        if (roleModel != null) {
            json.add("role_details", roleModel.getJsonObject());
        }
        if (creatorModel != null) {
            json.add("creator_details", creatorModel.getJsonObject());
        }
        if (modifierModel != null) {
            json.add("modifier_details", modifierModel.getJsonObject());
        }

        return json.build();
    }

    public UserModel getCreatorModel() {
        return creatorModel;
    }

    public void setCreatorModel(UserModel creatorModel) {
        this.creatorModel = creatorModel;
    }

    public UserModel getModifierModel() {
        return modifierModel;
    }

    public void setModifierModel(UserModel modifierModel) {
        this.modifierModel = modifierModel;
    }

    public EmployeeModel getEmployeeModel() {
        return employeeModel;
    }

    public void setEmployeeModel(EmployeeModel employeeModel) {
        this.employeeModel = employeeModel;
    }

    public RoleModel getRoleModel() {
        return roleModel;
    }

    public void setRoleModel(RoleModel roleModel) {
        this.roleModel = roleModel;
    }

    public long getUserid() {
        return userid;
    }

    public void setUserid(long userid) {
        this.userid = userid;
    }

    public long getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(long employeeid) {
        this.employeeid = employeeid;
    }

    public long getRoleid() {
        return roleid;
    }

    public void setRoleid(long roleid) {
        this.roleid = roleid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isState() {
        return state;
    }

    public void setState(boolean state) {
        this.state = state;
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
