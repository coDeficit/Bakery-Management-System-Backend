package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;

public class JobModel extends SuperModel {

    private int jobid;
    private String job_title;
    private String job_desc;
    public static final String sequence_id = "jobs_jobid_seq";

    public JobModel(int jobid, String job_title, String job_desc) {
        this.jobid = jobid;
        this.job_title = job_title;
        this.job_desc = job_desc;
    }

    public JobModel(String job_title, String job_desc) {
        this.job_title = job_title;
        this.job_desc = job_desc;
    }

    public JobModel(ResultSet set) throws Exception {
        this.jobid = set.getInt("jobid");
        this.job_title = set.getString("job_title");
        this.job_desc = set.getString("job_desc");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject json = null;

        json = Json.createObjectBuilder()
                .add("jobid", jobid)
                .add("job_title", job_title)
                .add("job_desc", job_desc)
                .build();

        return json;
    }

    public JobModel() {
    }

    public int getJobid() {
        return jobid;
    }

    public void setJobid(int jobid) {
        this.jobid = jobid;
    }

    public String getJob_title() {
        return job_title;
    }

    public void setJob_title(String job_title) {
        this.job_title = job_title;
    }

    public String getJob_desc() {
        return job_desc;
    }

    public void setJob_desc(String job_desc) {
        this.job_desc = job_desc;
    }

    
    //debug database response
    @Override
    public String __response() {
        return ("jobid: " + jobid
                + " job_title: " + job_title
                + " job_desc: " + job_desc);
    }

}
