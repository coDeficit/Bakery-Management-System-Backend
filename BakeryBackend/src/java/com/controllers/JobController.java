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
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/jobs")
public class JobController extends SuperController {

    public JobController() {
    }

    // return all jobs as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM jobs order by jobid");

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

        if (json != null) {
            return Response.status(Response.Status.OK).entity(builder.build().toString()).build();
        } else {
            return Response.status(Response.Status.NO_CONTENT).build();
        }

    }

    // return a single job as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") int jobid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = "SELECT * from jobs WHERE jobid = " + jobid;
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

    // create a job and return json object
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(JobModel model) throws SQLException {

        Response response = null;
        JobController jobController = new JobController();

        setPreparedStatement("INSERT INTO jobs (job_title, job_desc) VALUES (?,?)");
        preparedStatement.setString(1, model.getJob_title());
        preparedStatement.setString(2, model.getJob_desc());
        preparedStatement.executeUpdate();
        setCreateStatement();
        resultSet = statement.executeQuery("SELECT last_value FROM " + JobModel.sequence_id);

        while (resultSet.next()) {
            int jobid = resultSet.getInt("last_value");
            response = jobController.getById(jobid);
        }
        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // update a job and return json object
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") int jobid, JobModel model) throws SQLException {

        System.out.println("Calling JobController.update");
        JobModel job = null;
        Response response = null;
        JobController jobController = new JobController();

        setPreparedStatement("UPDATE jobs SET job_title=?, job_desc=? WHERE jobid = ?");
        preparedStatement.setString(1, model.getJob_title());
        preparedStatement.setString(2, model.getJob_desc());
        preparedStatement.setInt(3, jobid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery("SELECT * FROM jobs WHERE jobid = " + jobid);

        while (resultSet.next()) {
            response = jobController.getById(jobid);
        }

        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // delete a job and return response
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") int jobid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM jobs WHERE jobid = " + jobid);

            while (resultSet.next()) {
                JobModel job = new JobModel(resultSet);
                json = job.getJsonObject();
            }
            statement.execute("DELETE FROM jobs WHERE jobid = " + jobid);
            resultSet.close();
            statement.close();
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

}
