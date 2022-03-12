package com.models;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class UserModel extends SuperModel {

    private EmployeeModel employeeModel = null;
    private RoleModel roleModel = null;
    private int userid;
    private int employee;
    private int role;
    private String username;
    private String password;
    private boolean user_state;
    private Timestamp user_createdat;
    private Timestamp user_updatedat;
    public static final String user_sequence_id = "users_userid_seq";

    public UserModel(int employee, int role, String username, String password, boolean user_state, Timestamp user_createdat, Timestamp user_updatedat) {
        this.employee = employee;
        this.role = role;
        this.username = username;
        this.password = password;
        this.user_state = user_state;
        this.user_createdat = user_createdat;
        this.user_updatedat = user_updatedat;
    }

    public UserModel(int userid, int employee, int role, String username, String password, boolean user_state, Timestamp user_createdat, Timestamp user_updatedat) {
        this.userid = userid;
        this.employee = employee;
        this.role = role;
        this.username = username;
        this.password = password;
        this.user_state = user_state;
        this.user_createdat = user_createdat;
        this.user_updatedat = user_updatedat;
    }

    public UserModel(ResultSet set) throws SQLException, Exception {
        this.userid = set.getInt("userid");
        this.employee = set.getInt("employee");
        this.role = set.getInt("role");
        this.username = set.getString("username");
        this.password = set.getString("password");
        this.user_state = set.getBoolean("user_state");
        this.user_createdat = set.getTimestamp("user_createdat");
        this.user_updatedat = set.getTimestamp("user_updatedat");
        this.employeeModel = new EmployeeModel(set, false);
        this.roleModel = new RoleModel(set);
    }

    public UserModel(ResultSet set, boolean withEmp) throws SQLException, Exception {
        this.userid = set.getInt("userid");
        this.employee = set.getInt("employee");
        this.role = set.getInt("role");
        this.username = set.getString("username");
        this.password = set.getString("password");
        this.user_state = set.getBoolean("user_state");
        this.user_createdat = set.getTimestamp("user_createdat");
        this.user_updatedat = set.getTimestamp("user_updatedat");
        this.roleModel = new RoleModel(set);

        if (withEmp) {
            this.employeeModel = new EmployeeModel(set);
        }
    }

    public UserModel() {
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObjectBuilder json = null;

        json = Json.createObjectBuilder()
                .add("userid", userid)
                .add("employee", employee)
                .add("role", role)
                .add("username", username)
                .add("password", password)
                .add("user_state", user_state)
                .add("user_createdat", user_createdat.toString())
                .add("user_updatedat", user_updatedat.toString());

                if (employeeModel != null) {
                    json.add("employee_details", employeeModel.getJsonObject());
                }
                json.add("role_details", roleModel.getJsonObject());

        return json.build();
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

    public int getUserid() {
        return userid;
    }

    public void setUserid(int userid) {
        this.userid = userid;
    }

    public int getEmployee() {
        return employee;
    }

    public void setEmployee(int employee) {
        this.employee = employee;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
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

    public boolean isUser_state() {
        return user_state;
    }

    public void setUser_state(boolean user_state) {
        this.user_state = user_state;
    }

    public Timestamp getUser_createdat() {
        return user_createdat;
    }

    public void setUser_createdat(Timestamp user_createdat) {
        this.user_createdat = user_createdat;
    }

    public Timestamp getUser_updatedat() {
        return user_updatedat;
    }

    public void setUser_updatedat(Timestamp user_updatedat) {
        this.user_updatedat = user_updatedat;
    }

    
}
