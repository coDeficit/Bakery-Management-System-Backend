
package com.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;


public class PayMethodModel extends SuperModel {

    private long pay_methodid;
    private String pay_name;
    private String pay_provider;
    private String pay_image;
    private String pay_desc;
    public static final String sequence_id = "pay_methods_pay_methodid_seq";

    
    public PayMethodModel(ResultSet set) throws Exception {
        this.pay_methodid = set.getLong("pay_methodid");
        this.pay_name = set.getString("pay_name");
        this.pay_provider = set.getString("pay_provider");
        this.pay_image = set.getString("pay_image");
        this.pay_desc = set.getString("pay_desc");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject json = null;

        json = Json.createObjectBuilder()
                .add("pay_methodid", pay_methodid)
                .add("pay_name", pay_name)
                .add("pay_provider", pay_provider)
                .add("pay_image", pay_image)
                .add("pay_desc", pay_desc)
                .build();

        return json;
    }

    public PayMethodModel(long pay_methodid, String pay_name, String pay_provider, String pay_image, String pay_desc) {
        this.pay_methodid = pay_methodid;
        this.pay_name = pay_name;
        this.pay_provider = pay_provider;
        this.pay_image = pay_image;
        this.pay_desc = pay_desc;
    }

    public PayMethodModel(String pay_name, String pay_provider, String pay_image, String pay_desc) {
        this.pay_name = pay_name;
        this.pay_provider = pay_provider;
        this.pay_image = pay_image;
        this.pay_desc = pay_desc;
    }
    
    public PayMethodModel() {
    }
    
    public long getPay_methodid() {
        return pay_methodid;
    }

    public void setPay_methodid(long pay_methodid) {
        this.pay_methodid = pay_methodid;
    }

    public String getPay_name() {
        return pay_name;
    }

    public void setPay_name(String pay_name) {
        this.pay_name = pay_name;
    }

    public String getPay_provider() {
        return pay_provider;
    }

    public void setPay_provider(String pay_provider) {
        this.pay_provider = pay_provider;
    }

    public String getPay_image() {
        return pay_image;
    }

    public void setPay_image(String pay_image) {
        this.pay_image = pay_image;
    }

    public String getPay_desc() {
        return pay_desc;
    }

    public void setPay_desc(String pay_desc) {
        this.pay_desc = pay_desc;
    }
    
}
