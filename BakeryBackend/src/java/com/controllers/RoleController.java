
package com.controllers;

import com.models.RoleModel;
import com.models.UserModel;
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

@Path("/roles")
public class RoleController extends SuperController {

    String getAllQuery = "SELECT * FROM roles ";

    public RoleController() {
    }

    // return all roles as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();

            String query = getAllQuery + "order by roleid";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                RoleModel role = new RoleModel(resultSet);
                System.out.println("Displaying emptyp instance: " + role.__response());
                json = role.getJsonObject();
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

    // return a single role as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long roleid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = getAllQuery + "WHERE roleid = " + roleid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                RoleModel role = new RoleModel(resultSet);
                json = role.getJsonObject();
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

    // create an role and return json object
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(RoleModel model) throws SQLException {

        Response response = null;

        setPreparedStatement("INSERT INTO roles (name, permissions, description) VALUES (?,?,?)");
        preparedStatement.setString(1, model.getName());
        preparedStatement.setString(2, model.getPermissions());
        preparedStatement.setString(3, model.getDescription());
        preparedStatement.executeUpdate();
        setCreateStatement();

        resultSet = statement.executeQuery("SELECT last_value FROM " + RoleModel.sequence_id);

        while (resultSet.next()) {
            int roleid = resultSet.getInt("last_value");
            response = getById(roleid);
        }
        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // update an role and return json object
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long roleid, RoleModel model) throws SQLException {

        System.out.println("Calling RoleController.update");
        RoleModel role = null;
        Response response = null;

        String query = "UPDATE roles SET name=?, permissions=?, "
                + "description=? WHERE roleid = ?";
        setPreparedStatement(query);
        preparedStatement.setString(1, model.getName());
        preparedStatement.setString(2, model.getPermissions());
        preparedStatement.setString(3, model.getDescription());
        preparedStatement.setLong(4, roleid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery(getAllQuery + "WHERE roleid = " + roleid);

        while (resultSet.next()) {
            response = getById(roleid);
        }

        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // delete an role and return response
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long roleid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery(getAllQuery + "WHERE roleid = " + roleid);

            while (resultSet.next()) {
                RoleModel role = new RoleModel(resultSet);
                json = role.getJsonObject();
            }
            statement.execute("DELETE FROM roles WHERE roleid = " + roleid);
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
