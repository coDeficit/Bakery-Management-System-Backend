
package com.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.models.PayMethodModel;
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

@Path("/pay_methods")
public class PayMethodController extends SuperController {

    public PayMethodController() {
    }

    // return all pay_methods as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM pay_methods order by pay_methodid");

            while (resultSet.next()) {
                PayMethodModel pay_method = new PayMethodModel(resultSet);
                System.out.println("Displaying Job instance: " + pay_method.__response());
                json = pay_method.getJsonObject();
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

    // return a single pay_method as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long pay_methodid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = "SELECT * from pay_methods WHERE pay_methodid = " + pay_methodid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                PayMethodModel pay_method = new PayMethodModel(resultSet);
                json = pay_method.getJsonObject();
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

    // create a pay_method and return json object
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(PayMethodModel model) throws SQLException {

        Response response = null;
        PayMethodController PayMethodController = new PayMethodController();

        setPreparedStatement("INSERT INTO pay_methods (pay_name, pay_provider, pay_image, pay_desc) VALUES (?,?,?,?)");
        preparedStatement.setString(1, model.getPay_name());
        preparedStatement.setString(2, model.getPay_provider());
        preparedStatement.setString(3, model.getPay_image());
        preparedStatement.setString(4, model.getPay_desc());
        preparedStatement.executeUpdate();
        setCreateStatement();
        resultSet = statement.executeQuery("SELECT last_value FROM " + PayMethodModel.sequence_id);

        while (resultSet.next()) {
            int pay_methodid = resultSet.getInt("last_value");
            response = PayMethodController.getById(pay_methodid);
        }
        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // update a pay_method and return json object
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long pay_methodid, PayMethodModel model) throws SQLException {

        System.out.println("Calling PayMethodController.update");
        PayMethodModel pay_method = null;
        Response response = null;
        PayMethodController PayMethodController = new PayMethodController();

        setPreparedStatement("UPDATE pay_methods SET pay_name = ?, pay_provider = ?, pay_image = ?, pay_desc = ? WHERE pay_methodid = ?");
        preparedStatement.setString(1, model.getPay_name());
        preparedStatement.setString(2, model.getPay_provider());
        preparedStatement.setString(3, model.getPay_image());
        preparedStatement.setString(4, model.getPay_desc());
        preparedStatement.setLong(5, pay_methodid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery("SELECT * FROM pay_methods WHERE pay_methodid = " + pay_methodid);

        while (resultSet.next()) {
            response = PayMethodController.getById(pay_methodid);
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
    public Response delete(@PathParam("id") long pay_methodid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM pay_methods WHERE pay_methodid = " + pay_methodid);

            while (resultSet.next()) {
                PayMethodModel pay_method = new PayMethodModel(resultSet);
                json = pay_method.getJsonObject();
            }
            statement.execute("DELETE FROM pay_methods WHERE pay_methodid = " + pay_methodid);
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
