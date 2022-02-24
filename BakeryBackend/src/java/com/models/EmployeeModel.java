package com.models;

import com.controllers.SuperController;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class EmployeeModel extends SuperModel {
    private JobModel jobModel = null;
    private long employeeid;
    private long jobid;
    private String fullname;
    private String gender;
    private String phone;
    private String email;
    private String employ_type;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private long salary;
    private String image;
    private String status;
    private String notes;
    private Timestamp createdat;
    private Timestamp updatedat;
    public static final String sequence_id = "employees_employeeid_seq";

    public EmployeeModel(long jobid, long emptypeid, String fullname, String gender,
            String phone, String email, String employ_type, String address1, String address2, String city,
            String state, String country, long salary, String image, String status, 
            String notes, Timestamp createdat, Timestamp updatedat) {
        this.employeeid = employeeid;
        this.jobid = jobid;
        this.fullname = fullname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.employ_type = employ_type;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.salary = salary;
        this.image = image;
        this.status = status;
        this.notes = notes;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public EmployeeModel(long jobid, long emptypeid, String fullname, String gender,
    String phone, String email, String employ_type, String address1, String address2, 
    String city, String state, String country, long salary, String image, String status, 
    String notes) {
        this.jobid = jobid;
        this.fullname = fullname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.employ_type = employ_type;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.salary = salary;
        this.image = image;
        this.status = status;
        this.notes = notes;
    }

    public EmployeeModel(ResultSet set) throws SQLException, Exception {
        this.employeeid = set.getLong("employeeid");
        this.jobid = set.getLong("jobid");
        this.fullname = set.getString("fullname");
        this.gender = set.getString("gender");
        this.phone = set.getString("phone");
        this.email = set.getString("email");
        this.employ_type = set.getString("employ_type");
        this.address1 = set.getString("address1");
        this.address2 = set.getString("address2");
        this.city = set.getString("city");
        this.state = set.getString("state");
        this.country = set.getString("country");
        this.salary = set.getLong("salary");
        this.image = set.getString("image");
        this.status = set.getString("status");
        this.notes = set.getString("notes");
        this.createdat = set.getTimestamp("createdat");
        this.updatedat = set.getTimestamp("updatedat");
        this.jobModel = new JobModel(set);
    }

    public EmployeeModel(ResultSet set, boolean withJob, boolean withType) throws SQLException, Exception {
        this.employeeid = set.getLong("employeeid");
        this.jobid = set.getLong("jobid");
        this.fullname = set.getString("fullname");
        this.gender = set.getString("gender");
        this.phone = set.getString("phone");
        this.email = set.getString("email");
        this.employ_type = set.getString("employ_type");
        this.address1 = set.getString("address1");
        this.address2 = set.getString("address2");
        this.city = set.getString("city");
        this.state = set.getString("state");
        this.country = set.getString("country");
        this.salary = set.getLong("salary");
        this.image = set.getString("image");
        this.status = set.getString("status");
        this.notes = set.getString("notes");
        this.createdat = set.getTimestamp("createdat");
        this.updatedat = set.getTimestamp("updatedat");

        if (withJob) {
            this.jobModel = new JobModel(set);
        }
    }

    public EmployeeModel() {
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObjectBuilder json = null;

        json = Json.createObjectBuilder()
                .add("employeeid", employeeid)
                .add("jobid", jobid)
                .add("fullname", fullname)
                .add("gender", gender)
                .add("phone", phone)
                .add("email", email)
                .add("employ_type", employ_type)
                .add("address1", address1)
                .add("address2", address2)
                .add("city", city)
                .add("state", state)
                .add("country", country)
                .add("salary", salary)
                .add("image", image)
                .add("status", status)
                .add("notes", notes)
                .add("createdat", createdat.toString())
                .add("updatedat", updatedat.toString());

        if (jobModel != null) {
            json.add("job_details", jobModel.getJsonObject());
        }

        return json.build();
    }

    public JobModel getJobModel() {
        return jobModel;
    }

    public void setJobModel(JobModel jobModel) {
        this.jobModel = jobModel;
    }

    public long getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(long employeeid) {
        this.employeeid = employeeid;
    }

    public long getJobid() {
        return jobid;
    }

    public void setJobid(long jobid) {
        this.jobid = jobid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSalary() {
        return salary;
    }

    public void setSalary(long salary) {
        this.salary = salary;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
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

    public String getEmploy_type() {
        return employ_type;
    }

    public void setEmploy_type(String employ_type) {
        this.employ_type = employ_type;
    }

}