
package com.controllers;

import com.models.ItemModel;
import com.models.CategoryModel;
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

@Path("/items")
public class ItemController extends SuperController {

        String getAllQuery = "SELECT i.*, c.* FROM items i "
                + "LEFT JOIN categories c USING (categoryid) ";
    
        public ItemController() {
        }
    
        // return all items as json object
        @GET
        @Path("/all")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getAll() {
    
            JsonObject json = null;
            JsonArrayBuilder builder = Json.createArrayBuilder();
    
            try {
                setCreateStatement();
    
                String query = getAllQuery + "order by i.itemid";
    
                resultSet = statement.executeQuery(query);
    
                while (resultSet.next()) {
                    ItemModel item = new ItemModel(resultSet);
                    System.out.println("Displaying item instance: " + item.__response());
                    json = item.getJsonObject();
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
    
        // return a single item as json object
        @GET
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response getById(@PathParam("id") long itemid) {
            JsonObject json = null;
    
            try {
                setCreateStatement();
                String query = getAllQuery + "WHERE i.itemid = " + itemid;
                resultSet = statement.executeQuery(query);
    
                while (resultSet.next()) {
                    ItemModel item = new ItemModel(resultSet);
                    json = item.getJsonObject();
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
    
        // create an item and return json object
        @POST
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response create(ItemModel model) throws SQLException {
    
            Response response = null;
    
            String query = "insert into items (categoryid, item_name, item_color, "
            + "item_image, cost_price, unit_price, base_qty, current_qty, "
            + "stock_level, sku, item_desc, item_visible, item_avail) "
            + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    
            setPreparedStatement(query);
            preparedStatement.setLong(1, model.getCategoryid());
            preparedStatement.setString(2, model.getItem_name());
            preparedStatement.setString(3, model.getItem_color());
            preparedStatement.setString(4, model.getItem_image());
            preparedStatement.setLong(5, model.getCost_price());
            preparedStatement.setLong(6, model.getUnit_price());
            preparedStatement.setLong(7, model.getBase_qty());
            preparedStatement.setLong(8, model.getCurrent_qty());
            preparedStatement.setLong(9, model.getStock_level());
            preparedStatement.setString(10, model.getSku());
            preparedStatement.setString(11, model.getItem_desc());
            preparedStatement.setBoolean(12, model.isItem_visible());
            preparedStatement.setBoolean(13, model.isItem_avail());
            preparedStatement.executeUpdate();
            setCreateStatement();
    
            resultSet = statement.executeQuery("SELECT last_value FROM " + ItemModel.sequence_id);
    
            while (resultSet.next()) {
                int itemid = resultSet.getInt("last_value");
                response = getById(itemid);
            }
            
            resultSet.close();
            statement.close();
            preparedStatement.close();
    
            return response;
        }
    
        // update an item and return json object
        @PUT
        @Path("/{id}")
        @Consumes(MediaType.APPLICATION_JSON)
        @Produces(MediaType.APPLICATION_JSON)
        public Response update(@PathParam("id") long itemid, ItemModel model) throws SQLException {
    
            System.out.println("Calling ItemController.update");
            ItemModel item = null;
            Response response = null;
    
            String query = "UPDATE items SET categoryid = ?,  item_name = ?, item_color = ?, "
            + "item_image = ?, cost_price = ?, unit_price = ?, base_qty = ?, current_qty = ?, "
            + "stock_level = ?, sku = ?, item_desc = ?, item_visible = ?, item_avail = ? WHERE itemid = ?";
    
            setPreparedStatement(query);
            preparedStatement.setLong(1, model.getCategoryid());
            preparedStatement.setString(2, model.getItem_name());
            preparedStatement.setString(3, model.getItem_color());
            preparedStatement.setString(4, model.getItem_image());
            preparedStatement.setLong(5, model.getCost_price());
            preparedStatement.setLong(6, model.getUnit_price());
            preparedStatement.setLong(7, model.getBase_qty());
            preparedStatement.setLong(8, model.getCurrent_qty());
            preparedStatement.setLong(9, model.getStock_level());
            preparedStatement.setString(10, model.getSku());
            preparedStatement.setString(11, model.getItem_desc());
            preparedStatement.setBoolean(12, model.isItem_visible());
            preparedStatement.setBoolean(13, model.isItem_avail());
            preparedStatement.setLong(14, itemid);
            preparedStatement.executeUpdate();
    
            setCreateStatement();
            resultSet = statement.executeQuery(getAllQuery + "WHERE i.itemid = " + itemid);
    
            while (resultSet.next()) {
                response = getById(itemid);
                
            }
    
            resultSet.close();
            statement.close();
            preparedStatement.close();
    
            return response;
        }
    
        // delete an item and return response
        @DELETE
        @Path("/{id}")
        @Produces(MediaType.APPLICATION_JSON)
        public Response delete(@PathParam("id") long itemid) {
            JsonObject json = null;
    
            try {
                setCreateStatement();
                resultSet = statement.executeQuery(getAllQuery + "WHERE i.itemid = " + itemid);
    
                while (resultSet.next()) {
                    ItemModel item = new ItemModel(resultSet);
                    json = item.getJsonObject();
                }
                statement.execute("DELETE FROM items WHERE itemid = " + itemid);
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
