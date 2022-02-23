
package com.controllers;

import com.models.EmptypeModel;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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


@Path("/emptypes")
public class EmptypeController extends SuperController {

    public EmptypeController() {
    }
    
    // return all employment types as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM emptypes");

            while (resultSet.next()) {
                EmptypeModel emptype = new EmptypeModel(resultSet);
                System.out.println("Displaying emptyp instance: " + emptype.__response());
                json = emptype.getJsonObject();
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

    // return a single employment type as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long emptypeid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = "SELECT * from emptypes WHERE emptypeid = " + emptypeid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                EmptypeModel emptype = new EmptypeModel(resultSet);
                json = emptype.getJsonObject();
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

    // create an employment type and return json object
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(EmptypeModel model) throws SQLException {

        Response response = null;

        setPreparedStatement("INSERT INTO emptypes (name, notes) VALUES (?,?)");
        preparedStatement.setString(1, model.getName());
        preparedStatement.setString(2, model.getNotes());
        preparedStatement.executeUpdate();
        setCreateStatement();
        resultSet = statement.executeQuery("SELECT last_value FROM " + EmptypeModel.sequence_id);

        while (resultSet.next()) {
            int emptypeid = resultSet.getInt("last_value");
            response = getById(emptypeid);
        }
        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // update an employment type and return json object
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long emptypeid, EmptypeModel model) throws SQLException {

        System.out.println("Calling EmptypeController.update");
        EmptypeModel emptype = null;
        Response response = null;

        setPreparedStatement("UPDATE emptypes SET name=?, notes=? WHERE emptypeid = ?");
        preparedStatement.setString(1, model.getName());
        preparedStatement.setString(2, model.getNotes());
        preparedStatement.setLong(3, emptypeid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery("SELECT * FROM emptypes WHERE emptypeid = " + emptypeid);

        while (resultSet.next()) {
            response = getById(emptypeid);
        }

        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // delete an employment type and return response
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long emptypeid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM emptypes WHERE emptypeid = " + emptypeid);

            while (resultSet.next()) {
                EmptypeModel emptype = new EmptypeModel(resultSet);
                json = emptype.getJsonObject();
            }
            statement.execute("DELETE FROM emptypes WHERE emptypeid = " + emptypeid);
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
