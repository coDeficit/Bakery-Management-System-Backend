
package com.models;

import com.models.ItemModel;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;


public class ItemModel extends SuperModel {

    private CategoryModel categoryModel = null;
    private long itemid;
    private long categoryid;
    private String item_name;
    private String item_color;
    private String item_image;
    private long cost_price;
    private long unit_price;
    private long base_qty;
    private long current_qty;
    private long stock_level;
    private String sku;
    private String item_desc;
    private boolean item_visible; 
    private boolean item_avail;
    private Timestamp item_createdat;
    private Timestamp item_updatedat;
    public static final String sequence_id = "items_itemid_seq";
    

    public ItemModel(ResultSet set) throws SQLException, Exception {
        this.itemid = set.getLong("itemid");
        this.categoryid = set.getLong("categoryid");
        this.item_name = set.getString("item_name");
        this.item_color = set.getString("item_color");
        this.item_image = set.getString("item_image");
        this.cost_price = set.getLong("cost_price");
        this.unit_price = set.getLong("unit_price");
        this.base_qty = set.getLong("base_qty");
        this.current_qty = set.getLong("current_qty");
        this.stock_level = set.getLong("stock_level");
        this.sku = set.getString("sku");
        this.item_desc = set.getString("item_desc");
        this.item_visible = set.getBoolean("item_visible");
        this.item_avail = set.getBoolean("item_avail");
        this.item_createdat = set.getTimestamp("item_createdat");
        this.item_updatedat = set.getTimestamp("item_updatedat");
        this.categoryModel = new CategoryModel(set);
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObjectBuilder json = null;

        json = Json.createObjectBuilder()
                .add("itemid", itemid)
                .add("categoryid", categoryid)
                .add("item_name", item_name)
                .add("item_color", item_color)
                .add("item_image", item_image)
                .add("cost_price", cost_price)
                .add("unit_price", unit_price)
                .add("base_qty", base_qty)
                .add("current_qty", current_qty)
                .add("stock_level", stock_level)
                .add("sku", sku)
                .add("item_desc", item_desc)
                .add("item_visible", item_visible)
                .add("item_avail", item_avail)
                .add("item_createdat", item_createdat.toString())
                .add("item_updatedat", item_updatedat.toString());

                json.add("category_details", categoryModel.getJsonObject());

        return json.build();
    }

    public ItemModel(){

    }

    public ItemModel(long itemid, long categoryid, String item_name, String item_color, String item_image, long cost_price, long unit_price, long base_qty, long current_qty, long stock_level, String sku, String item_desc, boolean item_visible, boolean item_avail, Timestamp item_createdat, Timestamp item_updatedat) {
        this.itemid = itemid;
        this.categoryid = categoryid;
        this.item_name = item_name;
        this.item_color = item_color;
        this.item_image = item_image;
        this.cost_price = cost_price;
        this.unit_price = unit_price;
        this.base_qty = base_qty;
        this.current_qty = current_qty;
        this.stock_level = stock_level;
        this.sku = sku;
        this.item_desc = item_desc;
        this.item_visible = item_visible;
        this.item_avail = item_avail;
        this.item_createdat = item_createdat;
        this.item_updatedat = item_updatedat;
    }

    public ItemModel(long categoryid, String item_name, String item_color, String item_image, long cost_price, long unit_price, long base_qty, long current_qty, String sku, String item_desc, boolean item_visible, boolean item_avail) {
        this.categoryid = categoryid;
        this.item_name = item_name;
        this.item_color = item_color;
        this.item_image = item_image;
        this.cost_price = cost_price;
        this.unit_price = unit_price;
        this.base_qty = base_qty;
        this.current_qty = current_qty;
        this.sku = sku;
        this.item_desc = item_desc;
        this.item_visible = item_visible;
        this.item_avail = item_avail;
    }

    public CategoryModel getCategoryModel() {
        return categoryModel;
    }

    public void setCategoryModel(CategoryModel categoryModel) {
        this.categoryModel = categoryModel;
    }

    public long getItemid() {
        return itemid;
    }

    public void setItemid(long itemid) {
        this.itemid = itemid;
    }

    public long getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(long categoryid) {
        this.categoryid = categoryid;
    }

    public String getItem_name() {
        return item_name;
    }

    public void setItem_name(String item_name) {
        this.item_name = item_name;
    }

    public String getItem_color() {
        return item_color;
    }

    public void setItem_color(String item_color) {
        this.item_color = item_color;
    }

    public String getItem_image() {
        return item_image;
    }

    public void setItem_image(String item_image) {
        this.item_image = item_image;
    }

    public long getCost_price() {
        return cost_price;
    }

    public void setCost_price(long cost_price) {
        this.cost_price = cost_price;
    }

    public long getUnit_price() {
        return unit_price;
    }

    public void setUnit_price(long unit_price) {
        this.unit_price = unit_price;
    }

    public long getBase_qty() {
        return base_qty;
    }

    public void setBase_qty(long base_qty) {
        this.base_qty = base_qty;
    }

    public long getCurrent_qty() {
        return current_qty;
    }

    public void setCurrent_qty(long current_qty) {
        this.current_qty = current_qty;
    }

    public long getStock_level() {
        return stock_level;
    }

    public void setStock_level(long stock_level) {
        this.stock_level = stock_level;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getItem_desc() {
        return item_desc;
    }

    public void setItem_desc(String item_desc) {
        this.item_desc = item_desc;
    }

    public boolean isItem_visible() {
        return item_visible;
    }

    public void setItem_visible(boolean item_visible) {
        this.item_visible = item_visible;
    }

    public boolean isItem_avail() {
        return item_avail;
    }

    public void setItem_avail(boolean item_avail) {
        this.item_avail = item_avail;
    }

    public Timestamp getitem_Createdat() {
        return item_createdat;
    }

    public void setitem_Createdat(Timestamp item_createdat) {
        this.item_createdat = item_createdat;
    }

    public Timestamp getItem_Updatedat() {
        return item_updatedat;
    }

    public void setItem_Updatedat(Timestamp item_updatedat) {
        this.item_updatedat = item_updatedat;
    }

    
    
}
