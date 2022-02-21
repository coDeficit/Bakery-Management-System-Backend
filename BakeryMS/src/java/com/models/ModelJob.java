
package com.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;

/**
 *
 * @author CODEFICIT
 */
public class ModelJob extends BaseModel {
    
    private long jobid;
    private String title;
    private String description;
    private Timestamp createdat;
    private Timestamp updatedat;
    private static String id_sequence = "job_job_id_seq";

    //getters and setters
    
    
    public ModelJob(long jobid, String title, String description, Timestamp createdat, Timestamp updatedat) {
        this.jobid = jobid;
        this.title = title;
        this.description = description;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public ModelJob(long jobid, String title, String description) {
        this.jobid = jobid;
        this.title = title;
        this.description = description;
    }

    public ModelJob(String title, String description) {
        this.title = title;
        this.description = description;
    }
    
    public ModelJob(ResultSet set) throws Exception {
        this.jobid = set.getLong("jobid");
        this.title = set.getString("title");
        this.description = set.getString("description");
        this.createdat = set.getTimestamp("createdat");
        this.updatedat = set.getTimestamp("updatedat");
    }

    public ModelJob() {
        
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

    public Timestamp getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }
    
    
    @Override
    public JsonObject getJsonObject() {
        JsonObject json = Json.createObjectBuilder()
                .add("jobid", jobid)
                .add("title", title)
                .add("description", description)
                .add("createdat", createdat.toString())
                .add("updatedat", updatedat.toString()).build(); 
        
        return json;
    }
}

