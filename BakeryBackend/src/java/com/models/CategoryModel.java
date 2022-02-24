
package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;


public class CategoryModel extends SuperModel {

    private long categoryid;
    private String name = "";
    private String description = "";
    public static final String sequence_id = "categories_categoryid_seq";

    public CategoryModel(ResultSet set) throws Exception {
        this.categoryid = set.getLong("categoryid");
        this.name = set.getString("name");
        this.description = set.getString("description");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject json = null;

        json = Json.createObjectBuilder()
                .add("categoryid", categoryid)
                .add("name", name)
                .add("description", description)
                .build();

        return json;
    }


    public CategoryModel(){

    }

    public CategoryModel(long categoryid, String name, String description) {
        this.categoryid = categoryid;
        this.name = name;
        this.description = description;
    }

    public CategoryModel(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(long categoryid) {
        this.categoryid = categoryid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    
}
