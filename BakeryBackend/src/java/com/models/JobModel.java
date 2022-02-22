/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.models;

import java.sql.Timestamp;

/**
 *
 * @author CODEFICIT
 */
public class JobModel {
    private Long jobid;
    private String title;
    private String description;
    private Timestamp createdat;
    private Timestamp updatedat;

    public Long getJobid() {
        return jobid;
    }

    public void setJobid(Long jobid) {
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
    
    
}
