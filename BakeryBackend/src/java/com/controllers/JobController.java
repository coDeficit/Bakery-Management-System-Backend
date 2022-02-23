package com.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.models.JobModel;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jobs")
public class JobController extends SuperController {

    public JobController() {
    }

    //return all jobs as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        
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

            close();
        } catch (Exception e) {
            System.out.println("Error in query: " + e.getMessage());
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }

        return Response.status(Response.Status.OK).entity(builder.build().toString()).build();

    }

    
    //return a single job as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getSpecific(@PathParam("id") long instance_id) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = "SELECT * from jobs WHERE jobid = " + instance_id;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                JobModel Job = new JobModel(resultSet);
                json = Job.getJsonObject();
            }

            close();
        } catch (Exception e) {
            json = Json.createObjectBuilder().add("error", e.getMessage()).build();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(json.toString()).build();
        }

        if (json != null) {
            return Response.status(Response.Status.OK).entity(json.toString()).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }
    }


    //create a job and return json object
    @POST
    @Path("/create")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JobModel model) throws SQLException {
        
        Response response = null;
        
        setPreparedStatement("INSERT INTO jobs (title, description) VALUES (?,?)");
        preparedStatement.setString(1, model.getTitle());
        preparedStatement.setString(2, model.getDescription());
        preparedStatement.executeUpdate();
        setCreateStatement();
        resultSet = statement.executeQuery("SELECT last_value FROM " + JobModel.sequence_id);
        
        while (resultSet.next()) {
            int instance_id = resultSet.getInt("last_value");
            System.out.println("Instance id: " + instance_id);
            response = getSpecific(instance_id);
        }
        close();
        
        return response;
    }

}
