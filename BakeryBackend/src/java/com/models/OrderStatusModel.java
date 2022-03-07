
package com.models;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;


public class OrderStatusModel extends SuperModel {

    private long order_statusid;
    private String status_name = "";
    private String status_color = "";
    private String status_desc = "";
    public static final String sequence_id = "order_status_order_statusid_seq";

    public OrderStatusModel(long order_statusid, String status_name, String status_color, String status_desc) {
        this.order_statusid = order_statusid;
        this.status_name = status_name;
        this.status_color = status_color;
        this.status_desc = status_desc;
    }

    public OrderStatusModel(String status_name, String status_color, String status_desc) {
        this.status_name = status_name;
        this.status_color = status_color;
        this.status_desc = status_desc;
    }

    public OrderStatusModel(ResultSet set) throws Exception {
        this.order_statusid = set.getLong("order_statusid");
        this.status_name = set.getString("status_name");
        this.status_color = set.getString("status_color");
        this.status_desc = set.getString("status_desc");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObject json = null;

        json = Json.createObjectBuilder()
                .add("order_statusid", order_statusid)
                .add("status_name", status_name)
                .add("status_color", status_color)
                .add("status_desc", status_desc)
                .build();

        return json;
    }

    public OrderStatusModel() {
    }

    public long getOrder_statusid() {
        return order_statusid;
    }

    public void setOrder_statusid(long order_statusid) {
        this.order_statusid = order_statusid;
    }

    public String getStatus_name() {
        return status_name;
    }

    public void setStatus_name(String status_name) {
        this.status_name = status_name;
    }

    public String getStatus_color() {
        return status_color;
    }

    public void setStatus_color(String status_color) {
        this.status_color = status_color;
    }

    public String getStatus_desc() {
        return status_desc;
    }

    public void setStatus_desc(String status_desc) {
        this.status_desc = status_desc;
    }
    
}
