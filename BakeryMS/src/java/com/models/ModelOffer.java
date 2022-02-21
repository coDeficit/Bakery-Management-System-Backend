
package com.models;

import java.sql.Date;

public class ModelOffer {
    private int offerid;
    private float price;
    private Date startdate;
    private Date enddate;
    private Date datesave;
    private int quantity;
    private ModelProduct productid;

    public int getOfferid() {
        return offerid;
    }

    public void setOfferid(int offerid) {
        this.offerid = offerid;
    }

    public float getPrice() {
        return price;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public Date getStartdate() {
        return startdate;
    }

    public void setStartdate(Date startdate) {
        this.startdate = startdate;
    }

    public Date getEnddate() {
        return enddate;
    }

    public void setEnddate(Date enddate) {
        this.enddate = enddate;
    }

    public Date getDatesave() {
        return datesave;
    }

    public void setDatesave(Date datesave) {
        this.datesave = datesave;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public ModelProduct getProductid() {
        return productid;
    }

    public void setProductid(ModelProduct productid) {
        this.productid = productid;
    }
    
}
