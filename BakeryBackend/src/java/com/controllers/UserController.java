package com.controllers;

import com.models.UserModel;
import com.models.RoleModel;
import com.models.EmployeeModel;
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

@Path("/users")
public class UserController extends SuperController {

    String getAllQuery = "SELECT u.*, e.*, r.*, c.*, m.* FROM users u "
            + "LEFT JOIN employees e USING (employeeid) "
            + "LEFT JOIN roles r USING (roleid) "
            + "LEFT JOIN users c ON c.userid = u.createdby "
            + "LEFT JOIN users m ON m.userid = u.updatedby ";

    public UserController() {
    }

    // return all users as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();

            String query = getAllQuery + "order by u.userid";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                UserModel user = new UserModel(resultSet);
                System.out.println("Displaying user instance: " + user.__response());
                json = user.getJsonObject();
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

    // return a single user as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long userid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = getAllQuery + "WHERE u.userid = " + userid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                UserModel user = new UserModel(resultSet);
                json = user.getJsonObject();
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

    // create an user and return json object
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(UserModel model) throws SQLException {

        Response response = null;

        String query = "insert into users (employeeid, roleid, username, password, "
                + "state, createdby, updatedby) values "
                + "(?, ?, ?, crypt(?, gen_salt('bf', 8) ), ?, ?, ?)";

        setPreparedStatement(query);
        preparedStatement.setLong(1, model.getEmployeeid());
        preparedStatement.setLong(2, model.getRoleid());
        preparedStatement.setString(3, model.getUsername());
        preparedStatement.setString(4, model.getPassword());
        preparedStatement.setBoolean(5, model.isState());
        preparedStatement.setLong(6, model.getCreatedby());
        preparedStatement.setLong(7, model.getUpdatedby());
        preparedStatement.executeUpdate();
        setCreateStatement();

        resultSet = statement.executeQuery("SELECT last_value FROM " + UserModel.sequence_id);

//        while (resultSet.next()) {
//            int instance_id = resultSet.getInt("last_value");
//            System.out.println("Instance id: " + instance_id);
//
//            try (ResultSet set = statement.executeQuery("SELECT c.*, p.* FROM Mechanic c left join Person p ON c.person=p.person_id WHERE Mechanic_id=" + instance_id)) {
//                while (set.next()) {
//                    MechanicModel createdInstance = new MechanicModel(set, true);
//                    System.out.println("Created instance: " + createdInstance.__str__());
//                    json = createdInstance.getJsonObject();
//                }
//            }
//        }

        while (resultSet.next()) {
            int userid = resultSet.getInt("last_value");
            response = getById(userid);
        }
        
//        while (resultSet.next()) {
//            int userid = resultSet.getInt("last_value");
//            response = getById(userid);
//        }
        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // update an user and return json object
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long userid, UserModel model) throws SQLException {

        System.out.println("Calling userController.update");
        UserModel user = null;
        Response response = null;

        String query = "UPDATE users SET employeeid = ?, roleid = ?, username = ?, "
                + "password = crypt(?, gen_salt('bf', 8) ), state = ?, "
                + "createdby = ?, updatedby = ? WHERE userid = ?";

        setPreparedStatement(query);
        preparedStatement.setLong(1, model.getEmployeeid());
        preparedStatement.setLong(2, model.getRoleid());
        preparedStatement.setString(3, model.getUsername());
        preparedStatement.setString(4, model.getPassword());
        preparedStatement.setBoolean(5, model.isState());
        preparedStatement.setLong(6, model.getCreatedby());
        preparedStatement.setLong(7, model.getUpdatedby());
        preparedStatement.setLong(8, userid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery(getAllQuery + "WHERE u.userid = " + userid);

        //UserController userController = new UserController();

        while (resultSet.next()) {
            response = getById(userid);
            
        }

        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // delete an user and return response
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long userid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery(getAllQuery + "WHERE u.userid = " + userid);

            while (resultSet.next()) {
                UserModel user = new UserModel(resultSet);
                json = user.getJsonObject();
            }
            statement.execute("DELETE FROM users WHERE userid = " + userid);
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
