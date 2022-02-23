
package com.controllers;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.models.JobModel;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.core.Response;


@Path("jobs")
public class JobController extends SuperController{

    public JobController() {
    }
    
    @GET
    @Path("/listall")
    //@Produces({"MediaType.APPLICATION_JSON"})
    @Produces({"Application/json"})
    public Response getAll() {

        System.out.println("Running JobController.getAll()");
        JsonObject json;
        JsonArrayBuilder builder = Json.createArrayBuilder();
        
        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM jobs");
            
            while (resultSet.next()) {
                JobModel Job = new JobModel(resultSet);
                System.out.println("Displaying Job instance: " + Job.__response());
                json = Job.getJsonObject();
                builder.add(json);
            }
        } catch (Exception e) {
            System.out.println("Error in query: " + e.getMessage());
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }
        
        return Response.status(Response.Status.OK).entity(builder.build().toString()).build();
        
    }
}
