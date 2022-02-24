package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class CustomerModel extends SuperModel {

    private long customerid;
    private String fullname;
    private String gender;
    private String phone;
    private String email;
    private String address1;
    private String address2;
    private String city;
    private String state;
    private String country;
    private String image;
    private String comments;
    private Timestamp createdat;
    private Timestamp updatedat;
    public static final String sequence_id = "customers_customerid_seq";

    public CustomerModel(long customerid, String fullname, String gender,
            String phone, String email, String address1, String address2,
            String city, String state, String country, String image,
            String comments, Timestamp createdat, Timestamp updatedat) {
        this.customerid = customerid;
        this.fullname = fullname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.image = image;
        this.comments = comments;
        this.createdat = createdat;
        this.updatedat = updatedat;
    }

    public CustomerModel(String fullname, String gender,
            String phone, String email, String address1, String address2,
            String city, String state, String country, String image,
            String comments) {
        this.fullname = fullname;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.image = image;
        this.comments = comments;
    }

    public CustomerModel(ResultSet set) throws SQLException, Exception {
        this.customerid = set.getLong("customerid");
        this.fullname = set.getString("fullname");
        this.gender = set.getString("gender");
        this.phone = set.getString("phone");
        this.email = set.getString("email");
        this.address1 = set.getString("address1");
        this.address2 = set.getString("address2");
        this.city = set.getString("city");
        this.state = set.getString("state");
        this.country = set.getString("country");
        this.image = set.getString("image");
        this.comments = set.getString("comments");
        this.createdat = set.getTimestamp("createdat");
        this.updatedat = set.getTimestamp("updatedat");
    }

    public CustomerModel() {

    }

    @Override
    public JsonObject getJsonObject() {
        JsonObjectBuilder json = null;

        json = Json.createObjectBuilder()
                .add("customerid", customerid)
                .add("fullname", fullname)
                .add("gender", gender)
                .add("phone", phone)
                .add("email", email)
                .add("address1", address1)
                .add("address2", address2)
                .add("city", city)
                .add("state", state)
                .add("country", country)
                .add("image", image)
                .add("comments", comments)
                .add("createdat", createdat.toString())
                .add("updatedat", updatedat.toString());

        return json.build();               
    }

    public long getCustomerid() {
        return customerid;
    }

    public void setCustomerid(long customerid) {
        this.customerid = customerid;
    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress1() {
        return address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    public String getAddress2() {
        return address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public Timestamp getCreatedat() {
        return createdat;
    }

    public void setCreatedat(Timestamp createdat) {
        this.createdat = createdat;
    }

    public Timestamp getUpdatedat() {
        return updatedat;
    }

    public void setUpdatedat(Timestamp updatedat) {
        this.updatedat = updatedat;
    }
    
    

}
