/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.controller;

import com.models.ModelJob;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import javax.validation.Valid;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author CODEFICIT
 */

@Path("usercontroller")
public class UserController {
    
    @GET
    @Path("/listusers")
    @Produces({"application/json"})
    public ArrayList<ModelJob> listUsers() throws Exception {
        ArrayList<ModelJob> user_list = new ArrayList<>();

        try {
            Statement state = com.connexion.Connexion.seconnecter().createStatement();
            // ResultSet result = state.executeQuery("SELECT userid, name, email, phonenumber, login, pwd, dob, city, country, status, profile FROM casa_user");
            ResultSet result = state.executeQuery("SELECT * FROM casauser");

            // creating users and setting attributes
            while (result.next()) {
                ModelJob user = new ModelJob();
                user.setUserid(result.getInt("userid"));
                user.setName(result.getString("name"));
                user.setEmail(result.getString("email"));
                user.setPhonenumber(result.getString("phonenumber"));
                user.setLogin(result.getString("login"));
                user.setPassword(result.getString("password"));
                user.setDob(result.getDate("dob"));
                user.setCity(result.getString("city"));
                user.setCountry(result.getString("country"));
                user.setStatus(result.getString("status"));

                System.out.println(user.getName());

                user_list.add(user);
            }
            state.close();
            result.close();
        } catch (Exception e) {
            System.out.println("Error loading data : " + e.getMessage());
        }

        return user_list;
    }
    
    @POST
    @Path("/adduser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public ModelJob createUser(@Valid ModelJob user) {
        try {
            String sqlStatment= "Insert into casauser (name,email,phonenumber,login,password,dob,city,country,status) "+ "values (?,?,?,?,?,?,?,?,?)";
            System.out.println("Sql statement" + sqlStatment);
            PreparedStatement pstmt = com.connexion.Connexion.seconnecter().prepareStatement(sqlStatment);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhonenumber());
            pstmt.setString(4, user.getLogin());
            pstmt.setString(5, user.getPassword());
            pstmt.setDate(6, user.getDob());
            pstmt.setString(7, user.getCity());
            pstmt.setString(8, user.getCountry());
            pstmt.setString(9, user.getStatus());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error in query " + e.getMessage());
        }
        return user;
        //return "Product added";
    }
    
    @PUT
    @Path("/casauser/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public ModelJob updateUser(@PathParam("userid") int userid, @Valid ModelJob user){
        try {
           String sqlStatment= "update casauser set name=?,email=?,phonenumber=?,login=?,password=?,dob=?,city=?,country=?,status=? where userid =" + userid;
            PreparedStatement pstmt = com.connexion.Connexion.seconnecter().prepareStatement(sqlStatment);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhonenumber());
            pstmt.setString(4, user.getLogin());
            pstmt.setString(5, user.getPassword());
            pstmt.setDate(6, user.getDob());
            pstmt.setString(7, user.getCity());
            pstmt.setString(8, user.getCountry());
            pstmt.setString(9, user.getStatus());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error in query" + e.getMessage());
        }
        return user;
    }
    
    @PUT
    @Path("/upuserpass/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public ModelJob updateUserPswd(@PathParam("userid") int userid, @Valid ModelJob user){
        try {
           String sqlStatment= "update casauser set name=?,email=?,phonenumber=?,login=?,password=?,dob=?,city=?,country=?,status=? where userid =" + userid;
            PreparedStatement pstmt = com.connexion.Connexion.seconnecter().prepareStatement(sqlStatment);
            pstmt.setString(1, user.getName());
            pstmt.setString(2, user.getEmail());
            pstmt.setString(3, user.getPhonenumber());
            pstmt.setString(4, user.getLogin());
            pstmt.setString(5, user.getPassword());
            pstmt.setDate(6, user.getDob());
            pstmt.setString(7, user.getCity());
            pstmt.setString(8, user.getCountry());
            pstmt.setString(9, user.getStatus());
            pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error in query" + e.getMessage());
        }
        return user;
    }
    
//    @PUT
//    @Path("/updatepass/{userid}")
////    @Consumes(MediaType.APPLICATION_JSON)
////    @Produces({"application/json"})
//    public int updateUserPass(@PathParam("userid") int userid, String password){
//        int affectedrows = 0;
//        try {
//            ModelJob user = new ModelJob();
//            user.setPassword(password);
//            String sqlStatment= "update casauser set password=? where userid =" + userid;
//            PreparedStatement pstmt = com.connexion.Connexion.seconnecter().prepareStatement(sqlStatment);
//            pstmt.setString(1, user.getP);
//            affectedrows = pstmt.executeUpdate();
//        } catch (Exception e) {
//            System.out.println("error in update password query" + e.getMessage());
//        }
//        return affectedrows;
//    }
    
    @DELETE
    @Path("/casauser/{userid}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces({"application/json"})
    public String deleteUser(@PathParam("userid") int userid){
        try {
            String sqlStatment= "delete from casauser where userid =" + userid;
             PreparedStatement pstmt = com.connexion.Connexion.seconnecter().prepareStatement(sqlStatment);
             pstmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("error in query" + e.getMessage());
        }
        return "Deleted Successfully";
    }
}
