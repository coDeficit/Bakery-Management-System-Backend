
package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;


public class CategoryModel extends SuperModel {

    private long categoryid;
    private String cat_name = "";
    private String cat_desc = "";
    public static final String sequence_id = "categories_categoryid_seq";

    public CategoryModel(ResultSet set) throws Exception {
        this.categoryid = set.getLong("categoryid");
        this.cat_name = set.getString("cat_name");
        this.cat_desc = set.getString("cat_desc");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject json = null;

        json = Json.createObjectBuilder()
                .add("categoryid", categoryid)
                .add("cat_name", cat_name)
                .add("cat_desc", cat_desc)
                .build();

        return json;
    }


    public CategoryModel(){

    }

    public CategoryModel(long categoryid, String cat_name, String cat_desc) {
        this.categoryid = categoryid;
        this.cat_name = cat_name;
        this.cat_desc = cat_desc;
    }

    public CategoryModel(String cat_name, String cat_desc) {
        this.cat_name = cat_name;
        this.cat_desc = cat_desc;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(long categoryid) {
        this.categoryid = categoryid;
    }

    public String getCat_name() {
        return cat_name;
    }

    public void setCat_name(String cat_name) {
        this.cat_name = cat_name;
    }

    public String getCat_desc() {
        return cat_desc;
    }

    public void setCat_desc(String cat_desc) {
        this.cat_desc = cat_desc;
    }
    
}
