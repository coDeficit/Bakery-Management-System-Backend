
package com.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.models.CategoryModel;
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


@Path("/categories")
public class CategoryController extends SuperController {

    public CategoryController() {
    }

    // return all categories as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM categories order by categoryid ");

            while (resultSet.next()) {
                CategoryModel category = new CategoryModel(resultSet);
                System.out.println("Displaying category instance: " + category.__response());
                json = category.getJsonObject();
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
    public Response getById(@PathParam("id") long categoryid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = "SELECT * from categories WHERE categoryid = " + categoryid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                CategoryModel category = new CategoryModel(resultSet);
                json = category.getJsonObject();
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
    public Response create(CategoryModel model) throws SQLException {

        Response response = null;
        CategoryController CategoryController = new CategoryController();

        setPreparedStatement("INSERT INTO categories (name, description) VALUES (?,?)");
        preparedStatement.setString(1, model.getName());
        preparedStatement.setString(2, model.getDescription());
        preparedStatement.executeUpdate();
        setCreateStatement();
        resultSet = statement.executeQuery("SELECT last_value FROM " + CategoryModel.sequence_id);

        while (resultSet.next()) {
            int categoryid = resultSet.getInt("last_value");
            response = CategoryController.getById(categoryid);
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
    public Response update(@PathParam("id") long categoryid, CategoryModel model) throws SQLException {

        System.out.println("Calling CategoryController.update");
        CategoryModel category = null;
        Response response = null;
        CategoryController CategoryController = new CategoryController();

        setPreparedStatement("UPDATE categories SET name=?, description=? WHERE categoryid = ?");
        preparedStatement.setString(1, model.getName());
        preparedStatement.setString(2, model.getDescription());
        preparedStatement.setLong(3, categoryid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery("SELECT * FROM categories WHERE categoryid = " + categoryid);

        while (resultSet.next()) {
            response = CategoryController.getById(categoryid);
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
    public Response delete(@PathParam("id") long categoryid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM categories WHERE categoryid = " + categoryid);

            while (resultSet.next()) {
                CategoryModel category = new CategoryModel(resultSet);
                json = category.getJsonObject();
            }
            statement.execute("DELETE FROM categories WHERE categoryid = " + categoryid);
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
