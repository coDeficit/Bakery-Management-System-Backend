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
            + "INNER JOIN jobs j ON j.jobid = e.job ";

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
    public Response getById(@PathParam("id") int employeeid) {
        JsonObject json = null;

        try {
            setCreateStatement();
            String query = getAllQuery + "WHERE e.employeeid = " + employeeid;
            resultSet = statement.executeQuery(query);

            while (resultSet.next()) {
                EmployeeModel employee = new EmployeeModel(resultSet, true);
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
        
        String query = "insert into employees (job, emp_fullname, emp_gender, emp_phone, emp_email, employ_type, "
                + "emp_address1, emp_address2, emp_city, emp_state, emp_country, emp_salary, emp_image, emp_status, emp_notes) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        setPreparedStatement(query);
        preparedStatement.setInt(1, model.getJob());
        preparedStatement.setString(2, model.getEmp_fullname());
        preparedStatement.setString(3, model.getEmp_gender());
        preparedStatement.setString(4, model.getEmp_phone());
        preparedStatement.setString(5, model.getEmp_email());
        preparedStatement.setString(6, model.getEmploy_type());
        preparedStatement.setString(7, model.getEmp_address1());
        preparedStatement.setString(8, model.getEmp_address2());
        preparedStatement.setString(9, model.getEmp_city());
        preparedStatement.setString(10, model.getEmp_state());
        preparedStatement.setString(11, model.getEmp_country());
        preparedStatement.setInt(12, model.getEmp_salary());
        preparedStatement.setString(13, model.getEmp_image());
        preparedStatement.setString(14, model.getEmp_status());
        preparedStatement.setString(15, model.getEmp_notes());
        preparedStatement.executeUpdate();
        setCreateStatement();

        resultSet = statement.executeQuery("SELECT last_value FROM " + EmployeeModel.emp_sequence_id);
        

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
    public Response update(@PathParam("id") int employeeid, EmployeeModel model) throws SQLException {

        System.out.println("Calling employeeController.update");
        EmployeeModel employee = null;
        Response response = null;
        EmployeeController employeeController = new EmployeeController();

        String query = "UPDATE employees SET job = ?, emp_fullname = ?, emp_gender = ?, emp_"
                + "phone = ?, emp_email = ?, employ_type = ?, emp_address1 = ?, emp_address2 = ?, emp_"
                + "city = ?, emp_state = ?, emp_country = ?, emp_salary = ?, emp_image = ?, emp_"
                + "status = ?, emp_notes = ? WHERE employeeid = ?";

        setPreparedStatement(query);

        preparedStatement.setInt(1, model.getJob());
        preparedStatement.setString(2, model.getEmp_fullname());
        preparedStatement.setString(3, model.getEmp_gender());
        preparedStatement.setString(4, model.getEmp_phone());
        preparedStatement.setString(5, model.getEmp_email());
        preparedStatement.setString(6, model.getEmploy_type());
        preparedStatement.setString(7, model.getEmp_address1());
        preparedStatement.setString(8, model.getEmp_address2());
        preparedStatement.setString(9, model.getEmp_city());
        preparedStatement.setString(10, model.getEmp_state());
        preparedStatement.setString(11, model.getEmp_country());
        preparedStatement.setInt(12, model.getEmp_salary());
        preparedStatement.setString(13, model.getEmp_image());
        preparedStatement.setString(14, model.getEmp_status());
        preparedStatement.setString(15, model.getEmp_notes());
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
    public Response delete(@PathParam("id") int employeeid) {
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
