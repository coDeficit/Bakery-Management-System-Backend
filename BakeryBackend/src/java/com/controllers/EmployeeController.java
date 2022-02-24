package com.controllers;

import com.models.EmployeeModel;
import com.models.JobModel;
import com.models.UserModel;
import java.sql.ResultSet;
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

@Path("/employees")
public class EmployeeController extends SuperController {

    String getAllQuery = "SELECT e.*, j.* FROM employees e "
            + "INNER JOIN jobs j USING (jobid) ";

    public EmployeeController() {
    }

    // return all employees as json object
    @GET
    @Path("/all")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {

        JsonObject json = null;
        JsonArrayBuilder builder = Json.createArrayBuilder();

        try {
            setCreateStatement();

            String query = getAllQuery + "order by e.employeeid";

            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                EmployeeModel employee = new EmployeeModel(resultSet);
                System.out.println("Displaying emptyp instance: " + employee.__response());
                json = employee.getJsonObject();
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

    // return a single employee as json object
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getById(@PathParam("id") long employeeid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = getAllQuery + "WHERE e.employeeid = " + employeeid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                EmployeeModel employee = new EmployeeModel(resultSet, true, true);
                json = employee.getJsonObject();
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

    // create an employee and return json object
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response create(EmployeeModel model) throws SQLException, Exception {

        Response response = null;
        
        String query = "insert into employees (jobid, fullname, gender, phone, email, employ_type, "
                + "address1, address2, city, state, country, salary, image, status, notes) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        setPreparedStatement(query);
        preparedStatement.setLong(1, model.getJobid());
        preparedStatement.setString(2, model.getFullname());
        preparedStatement.setString(3, model.getGender());
        preparedStatement.setString(4, model.getPhone());
        preparedStatement.setString(5, model.getEmail());
        preparedStatement.setString(6, model.getEmploy_type());
        preparedStatement.setString(7, model.getAddress1());
        preparedStatement.setString(8, model.getAddress2());
        preparedStatement.setString(9, model.getCity());
        preparedStatement.setString(10, model.getState());
        preparedStatement.setString(11, model.getCountry());
        preparedStatement.setLong(12, model.getSalary());
        preparedStatement.setString(13, model.getImage());
        preparedStatement.setString(14, model.getStatus());
        preparedStatement.setString(15, model.getNotes());
        preparedStatement.executeUpdate();
        setCreateStatement();

        resultSet = statement.executeQuery("SELECT last_value FROM " + EmployeeModel.sequence_id);
        

        while (resultSet.next()) {
            int employeeid = resultSet.getInt("last_value");
            response = getById(employeeid);
        }
        
        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // update an employee and return json object
    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") long employeeid, EmployeeModel model) throws SQLException {

        System.out.println("Calling employeeController.update");
        EmployeeModel employee = null;
        Response response = null;
        EmployeeController employeeController = new EmployeeController();

        String query = "UPDATE employees SET jobid = ?, fullname = ?, gender = ?, "
                + "phone = ?, email = ?, employ_type = ?, address1 = ?, address2 = ?, "
                + "city = ?, state = ?, country = ?, salary = ?, image = ?, "
                + "status = ?, notes = ? WHERE employeeid = ?";

        setPreparedStatement(query);

        preparedStatement.setLong(1, model.getJobid());
        preparedStatement.setString(2, model.getFullname());
        preparedStatement.setString(3, model.getGender());
        preparedStatement.setString(4, model.getPhone());
        preparedStatement.setString(5, model.getEmail());
        preparedStatement.setString(6, model.getEmploy_type());
        preparedStatement.setString(7, model.getAddress1());
        preparedStatement.setString(8, model.getAddress2());
        preparedStatement.setString(9, model.getCity());
        preparedStatement.setString(10, model.getState());
        preparedStatement.setString(11, model.getCountry());
        preparedStatement.setLong(12, model.getSalary());
        preparedStatement.setString(13, model.getImage());
        preparedStatement.setString(14, model.getStatus());
        preparedStatement.setString(15, model.getNotes());
        preparedStatement.setLong(16, employeeid);
        preparedStatement.executeUpdate();

        setCreateStatement();
        resultSet = statement.executeQuery(getAllQuery + "WHERE e.employeeid = " + employeeid);

        while (resultSet.next()) {
            response = employeeController.getById(employeeid);
        }

        resultSet.close();
        statement.close();
        preparedStatement.close();

        return response;
    }

    // delete an employee and return response
    @DELETE
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@PathParam("id") long employeeid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            resultSet = statement.executeQuery(getAllQuery + "WHERE e.employeeid = " + employeeid);

            while (resultSet.next()) {
                EmployeeModel employee = new EmployeeModel(resultSet);
                json = employee.getJsonObject();
            }
            statement.execute("DELETE FROM employees WHERE employeeid = " + employeeid);
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
