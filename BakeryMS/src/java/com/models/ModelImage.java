package com.models;

public class ModelImage {
    private int imageid;
    private String name;
    private String description;
    private String image;
    private ModelProduct productid;

    public int getImageid() {
        return imageid;
    }

    public void setImageid(int imageid) {
        this.imageid = imageid;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public ModelProduct getProductid() {
        return productid;
    }

    public void setProductid(ModelProduct productid) {
        this.productid = productid;
    }

    
}
