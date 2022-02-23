package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;

public class JobModel extends SuperModel {

    private long jobid;
    private String title;
    private String description;
    private Timestamp createdat;
    private Timestamp updatedat;
    public static final String sequence_id = "jobs_jobid_seq";

    public JobModel(long jobid, String title, String description, Timestamp createdat, Timestamp updatedat) {
        this.jobid = jobid;
        this.title = title;
        this.description = description;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public JobModel(String title, String description) {
        this.title = title;
        this.description = description;
    }

    public JobModel(ResultSet set) throws Exception {
        this.jobid = set.getLong("jobid");
        this.title = set.getString("title");
        this.description = set.getString("description");
        this.createdat = set.getTimestamp("createdat");
        this.updatedat = set.getTimestamp("updatedat");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject json = null;

        json = Json.createObjectBuilder()
                .add("jobid", jobid)
                .add("title", title)
                .add("description", description)
                .add("createdat", createdat.toString())
                .add("updatedat", updatedat.toString())
                .build();

        return json;
    }

    public JobModel() {
    }

    public long getJobid() {
        return jobid;
    }

    public void setJobid(long jobid) {
        this.jobid = jobid;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public Timestamp getUpdateat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }

    //debug database response
    @Override
    public String __response() {
        return ("jobid: " + jobid
                + " title: " + title
                + " description: " + description
                + " createdat: " + createdat.toString()
                + " updatedat: " + updatedat.toString());
    }

}
