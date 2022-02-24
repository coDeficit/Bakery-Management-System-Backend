
package com.controllers;

import com.models.CustomerModel;
import java.sql.SQLException;
import javax.json.Json;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;


@Path("/customers")
public class CustomerController extends SuperController {

    String getAllQuery = "SELECT * FROM customers c ";

    public CustomerController() {
    }

    // return all customers as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();

            String query = getAllQuery + "order by c.customerid";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                CustomerModel customer = new CustomerModel(resultSet);
                System.out.println("Displaying user instance: " + customer.__response());
                json = customer.getJsonObject();
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

    // return a single customer as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long customerid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = getAllQuery + "WHERE c.customerid = " + customerid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                CustomerModel customer = new CustomerModel(resultSet);
                json = customer.getJsonObject();
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

    // create an customer and return json object
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(CustomerModel model) throws SQLException {

        Response response = null;

        String query = "insert into customers (fullname, gender, phone, email, "
        + "address1, address2, city, state, country, image, comments) "
        + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        setPreparedStatement(query);
        preparedStatement.setString(1, model.getFullname());
        preparedStatement.setString(2, model.getGender());
        preparedStatement.setString(3, model.getPhone());
        preparedStatement.setString(4, model.getEmail());
        preparedStatement.setString(5, model.getAddress1());
        preparedStatement.setString(6, model.getAddress2());
        preparedStatement.setString(7, model.getCity());
        preparedStatement.setString(8, model.getState());
        preparedStatement.setString(9, model.getCountry());
        preparedStatement.setString(10, model.getImage());
        preparedStatement.setString(11, model.getComments());
        preparedStatement.executeUpdate();
        setCreateStatement();

        resultSet = statement.executeQuery("SELECT last_value FROM " + CustomerModel.sequence_id);

        while (resultSet.next()) {
            int customerid = resultSet.getInt("last_value");
            response = getById(customerid);
        }
        
        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // update an customer and return json object
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long customerid, CustomerModel model) throws SQLException {

        System.out.println("Calling CustomerController.update");
        CustomerModel customer = null;
        Response response = null;

        String query = "UPDATE customers SET fullname = ?, gender = ?, "
        + "phone = ?, email = ?, address1 = ?, address2 = ?, "
        + "city = ?, state = ?, country = ?, image = ?, comments = ? WHERE customerid = ?";

        setPreparedStatement(query);
        preparedStatement.setString(1, model.getFullname());
        preparedStatement.setString(2, model.getGender());
        preparedStatement.setString(3, model.getPhone());
        preparedStatement.setString(4, model.getEmail());
        preparedStatement.setString(5, model.getAddress1());
        preparedStatement.setString(6, model.getAddress2());
        preparedStatement.setString(7, model.getCity());
        preparedStatement.setString(8, model.getState());
        preparedStatement.setString(9, model.getCountry());
        preparedStatement.setString(10, model.getImage());
        preparedStatement.setString(11, model.getComments());
        preparedStatement.setLong(12, customerid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery(getAllQuery + "WHERE c.customerid = " + customerid);

        while (resultSet.next()) {
            response = getById(customerid);
            
        }

        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // delete an customer and return response
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long customerid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery(getAllQuery + "WHERE c.customerid = " + customerid);

            while (resultSet.next()) {
                CustomerModel customer = new CustomerModel(resultSet);
                json = customer.getJsonObject();
            }
            statement.execute("DELETE FROM customers WHERE customerid = " + customerid);
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
