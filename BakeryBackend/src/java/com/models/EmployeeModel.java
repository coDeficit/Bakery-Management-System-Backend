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
    private int employeeid;
    private int job;
    private String emp_fullname;
    private String emp_gender;
    private String emp_phone;
    private String emp_email;
    private String employ_type;
    private String emp_address1;
    private String emp_address2;
    private String emp_city;
    private String emp_state;
    private String emp_country;
    private int emp_salary;
    private String emp_image;
    private String emp_status;
    private String emp_notes;
    private Timestamp emp_createdat;
    private Timestamp emp_updatedat;
    public static final String emp_sequence_id = "employees_employeeid_seq";

    public EmployeeModel(int job, int emptypeid, String emp_fullname, String emp_gender,
            String emp_phone, String emp_email, String employ_type, String emp_address1, String emp_address2, String emp_city,
            String emp_state, String emp_country, int emp_salary, String emp_image, String emp_status, 
            String emp_notes, Timestamp emp_createdat, Timestamp emp_updatedat) {
        this.employeeid = employeeid;
        this.job = job;
        this.emp_fullname = emp_fullname;
        this.emp_gender = emp_gender;
        this.emp_phone = emp_phone;
        this.emp_email = emp_email;
        this.employ_type = employ_type;
        this.emp_address1 = emp_address1;
        this.emp_address2 = emp_address2;
        this.emp_city = emp_city;
        this.emp_state = emp_state;
        this.emp_country = emp_country;
        this.emp_salary = emp_salary;
        this.emp_image = emp_image;
        this.emp_status = emp_status;
        this.emp_notes = emp_notes;
        this.emp_createdat = emp_createdat;
        this.emp_updatedat = emp_updatedat;
    }

    public EmployeeModel(int job, int emptypeid, String emp_emp_fullname, String emp_gender,
    String emp_phone, String emp_email, String employ_type, String emp_address1, String emp_address2, 
    String emp_city, String emp_state, String emp_country, int emp_salary, String emp_image, String emp_status, 
    String emp_notes) {
        this.job = job;
        this.emp_fullname = emp_fullname;
        this.emp_gender = emp_gender;
        this.emp_phone = emp_phone;
        this.emp_email = emp_email;
        this.employ_type = employ_type;
        this.emp_address1 = emp_address1;
        this.emp_address2 = emp_address2;
        this.emp_city = emp_city;
        this.emp_state = emp_state;
        this.emp_country = emp_country;
        this.emp_salary = emp_salary;
        this.emp_image = emp_image;
        this.emp_status = emp_status;
        this.emp_notes = emp_notes;
    }

    public EmployeeModel(ResultSet set) throws SQLException, Exception {
        this.employeeid = set.getInt("employeeid");
        this.job = set.getInt("job");
        this.emp_fullname = set.getString("emp_fullname");
        this.emp_gender = set.getString("emp_gender");
        this.emp_phone = set.getString("emp_phone");
        this.emp_email = set.getString("emp_email");
        this.employ_type = set.getString("employ_type");
        this.emp_address1 = set.getString("emp_address1");
        this.emp_address2 = set.getString("emp_address2");
        this.emp_city = set.getString("emp_city");
        this.emp_state = set.getString("emp_state");
        this.emp_country = set.getString("emp_country");
        this.emp_salary = set.getInt("emp_salary");
        this.emp_image = set.getString("emp_image");
        this.emp_status = set.getString("emp_status");
        this.emp_notes = set.getString("emp_notes");
        this.emp_createdat = set.getTimestamp("emp_createdat");
        this.emp_updatedat = set.getTimestamp("emp_updatedat");
        this.jobModel = new JobModel(set);
    }

    public EmployeeModel(ResultSet set, boolean withJob) throws SQLException, Exception {
        this.employeeid = set.getInt("employeeid");
        this.job = set.getInt("job");
        this.emp_fullname = set.getString("emp_fullname");
        this.emp_gender = set.getString("emp_gender");
        this.emp_phone = set.getString("emp_phone");
        this.emp_email = set.getString("emp_email");
        this.employ_type = set.getString("employ_type");
        this.emp_address1 = set.getString("emp_address1");
        this.emp_address2 = set.getString("emp_address2");
        this.emp_city = set.getString("emp_city");
        this.emp_state = set.getString("emp_state");
        this.emp_country = set.getString("emp_country");
        this.emp_salary = set.getInt("emp_salary");
        this.emp_image = set.getString("emp_image");
        this.emp_status = set.getString("emp_status");
        this.emp_notes = set.getString("emp_notes");
        this.emp_createdat = set.getTimestamp("emp_createdat");
        this.emp_updatedat = set.getTimestamp("emp_updatedat");

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
                .add("job", job)
                .add("emp_fullname", emp_fullname)
                .add("emp_gender", emp_gender)
                .add("emp_phone", emp_phone)
                .add("emp_email", emp_email)
                .add("employ_type", employ_type)
                .add("emp_address1", emp_address1)
                .add("emp_address2", emp_address2)
                .add("emp_city", emp_city)
                .add("emp_state", emp_state)
                .add("emp_country", emp_country)
                .add("emp_salary", emp_salary)
                .add("emp_image", emp_image)
                .add("emp_status", emp_status)
                .add("emp_notes", emp_notes)
                .add("emp_createdat", emp_createdat.toString())
                .add("emp_updatedat", emp_updatedat.toString());

        if (jobModel != null) {
            json.add("job_details", jobModel.getJsonObject());
        }

        return json.build();
    }

    public String getEmp_fullname() {
        return emp_fullname;
    }

    public void setEmp_fullname(String emp_fullname) {
        this.emp_fullname = emp_fullname;
    }

    public String getEmp_gender() {
        return emp_gender;
    }

    public void setEmp_gender(String emp_gender) {
        this.emp_gender = emp_gender;
    }

    public String getEmp_email() {
        return emp_email;
    }

    public void setEmp_email(String emp_email) {
        this.emp_email = emp_email;
    }

    public String getEmp_address1() {
        return emp_address1;
    }

    public void setEmp_address1(String emp_address1) {
        this.emp_address1 = emp_address1;
    }

    public String getEmp_address2() {
        return emp_address2;
    }

    public void setEmp_address2(String emp_address2) {
        this.emp_address2 = emp_address2;
    }

    public String getEmp_city() {
        return emp_city;
    }

    public void setEmp_city(String emp_city) {
        this.emp_city = emp_city;
    }

    public String getEmp_country() {
        return emp_country;
    }

    public void setEmp_country(String emp_country) {
        this.emp_country = emp_country;
    }

    public String getEmp_image() {
        return emp_image;
    }

    public void setEmp_image(String emp_image) {
        this.emp_image = emp_image;
    }

    public String getEmp_notes() {
        return emp_notes;
    }

    public void setEmp_notes(String emp_notes) {
        this.emp_notes = emp_notes;
    }

    public Timestamp getEmp_createdat() {
        return emp_createdat;
    }

    public void setEmp_createdat(Timestamp emp_createdat) {
        this.emp_createdat = emp_createdat;
    }

    public JobModel getJobModel() {
        return jobModel;
    }

    public void setJobModel(JobModel jobModel) {
        this.jobModel = jobModel;
    }

    public int getEmployeeid() {
        return employeeid;
    }

    public void setEmployeeid(int employeeid) {
        this.employeeid = employeeid;
    }

    public int getJob() {
        return job;
    }

    public void setJob(int job) {
        this.job = job;
    }

    public String getEmp_phone() {
        return emp_phone;
    }

    public void setEmp_phone(String emp_phone) {
        this.emp_phone = emp_phone;
    }

    public String getEmploy_type() {
        return employ_type;
    }

    public void setEmploy_type(String employ_type) {
        this.employ_type = employ_type;
    }

    public String getEmp_state() {
        return emp_state;
    }

    public void setEmp_state(String emp_state) {
        this.emp_state = emp_state;
    }

    public int getEmp_salary() {
        return emp_salary;
    }

    public void setEmp_salary(int emp_salary) {
        this.emp_salary = emp_salary;
    }

    public String getEmp_status() {
        return emp_status;
    }

    public void setEmp_status(String emp_status) {
        this.emp_status = emp_status;
    }

    public Timestamp getEmp_updatedat() {
        return emp_updatedat;
    }

    public void setEmp_updatedat(Timestamp emp_updatedat) {
        this.emp_updatedat = emp_updatedat;
    }

    
}
