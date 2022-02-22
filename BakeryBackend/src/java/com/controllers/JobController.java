
package com.controllers;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.models.JobModel;


@Path("jobs")
public class JobController {

    public JobController() {
    }
    
    @GET
    @Path("/listall")
    @Produces({"application/json"})
    public ArrayList<JobModel> listUsers() throws Exception {
        ArrayList<JobModel> job_list = new ArrayList<>();

        try {
            Statement state = com.connexion.Connexion.seconnecter().createStatement();
            // ResultSet result = state.executeQuery("SELECT userid, name, email, phonenumber, login, pwd, dob, city, country, status, profile FROM casa_user");
            ResultSet result = state.executeQuery("SELECT * FROM jobs");

            // creating users and setting attributes
            while (result.next()) {
                JobModel job = new JobModel();
                job.setJobid(result.getLong("jobid"));
                job.setTitle(result.getString("title"));
                job.setDescription(result.getString("description"));
                job.setCreatedat(result.getTimestamp("createdat"));
                job.setUpdatedat(result.getTimestamp("updatedat"));

                System.out.println(job.getTitle());

                job_list.add(job);
            }
            state.close();
            result.close();
        } catch (Exception e) {
            System.out.println("Error loading data : " + e.getMessage());
        }

        return job_list;
    }
}
