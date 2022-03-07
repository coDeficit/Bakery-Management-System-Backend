
package com.controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import com.models.OrderStatusModel;
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

@Path("/order_status")
public class OrderStatusController extends SuperController {

    public OrderStatusController() {
    }

    // return all order_status as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM order_status order by order_statusid");

            while (resultSet.next()) {
                OrderStatusModel Order_status = new OrderStatusModel(resultSet);
                System.out.println("Displaying Order_status instance: " + Order_status.__response());
                json = Order_status.getJsonObject();
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

    // return a single Order_status as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long order_statusid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = "SELECT * from order_status WHERE order_statusid = " + order_statusid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                OrderStatusModel Order_status = new OrderStatusModel(resultSet);
                json = Order_status.getJsonObject();
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

    // create a Order_status and return json object
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(OrderStatusModel model) throws SQLException {

        Response response = null;
        OrderStatusController OrderStatusController = new OrderStatusController();

        setPreparedStatement("INSERT INTO order_status (status_name, status_color, status_desc) VALUES (?,?,?)");
        preparedStatement.setString(1, model.getStatus_name());
        preparedStatement.setString(2, model.getStatus_color());
        preparedStatement.setString(3, model.getStatus_desc());
        preparedStatement.executeUpdate();
        setCreateStatement();
        resultSet = statement.executeQuery("SELECT last_value FROM " + OrderStatusModel.sequence_id);

        while (resultSet.next()) {
            int order_statusid = resultSet.getInt("last_value");
            response = OrderStatusController.getById(order_statusid);
        }
        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // update a Order_status and return json object
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long order_statusid, OrderStatusModel model) throws SQLException {

        System.out.println("Calling OrderStatusController.update");
        OrderStatusModel Order_status = null;
        Response response = null;
        OrderStatusController OrderStatusController = new OrderStatusController();

        setPreparedStatement("UPDATE order_status SET status_name=?, status_color=?, status_desc=? WHERE order_statusid = ?");
        preparedStatement.setString(1, model.getStatus_name());
        preparedStatement.setString(2, model.getStatus_color());
        preparedStatement.setString(3, model.getStatus_desc());
        preparedStatement.setLong(4, order_statusid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery("SELECT * FROM order_status WHERE order_statusid = " + order_statusid);

        while (resultSet.next()) {
            response = OrderStatusController.getById(order_statusid);
        }

        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // delete a Order_status and return response
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long order_statusid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery("SELECT * FROM order_status WHERE order_statusid = " + order_statusid);

            while (resultSet.next()) {
                OrderStatusModel Order_status = new OrderStatusModel(resultSet);
                json = Order_status.getJsonObject();
            }
            statement.execute("DELETE FROM order_status WHERE order_statusid = " + order_statusid);
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
