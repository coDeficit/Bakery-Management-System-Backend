package com.models;

import javax.json.JsonObject;

public abstract class BaseModel {
    static String id_sequence;
    
    public String __str__() {
        return "BaseModel";
    }
    
    public abstract JsonObject getJsonObject();
}
