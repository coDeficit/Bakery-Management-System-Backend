
package com.controller;
import com.models.ModelJob;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author CODEFICIT
 */

@Path("/jobs")
public class JobController extends BaseController {
    
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<ModelJob> getAll() {
        ArrayList<ModelJob> jobs = new ArrayList<>();

        try {
            createStatement();
            resultSet = statement.executeQuery("SELECT * FROM Jobs ORDER BY jobid");
            
            while (resultSet.next()) {
                ModelJob job = new ModelJob(resultSet);
                System.out.println("Printing job: " + job.test_str());
                jobs.add(job);
            }
            
//            resultSet.close();
//            statement.close();
        } catch (Exception e) {
            System.out.println("Error in query: " + e.getMessage());
        }
        return jobs;
    }
}
