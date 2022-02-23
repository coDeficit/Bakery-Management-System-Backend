
package com.models;

import javax.json.JsonObject;


//baae model for all models
public abstract class SuperModel {
    static String sequence_id;
    
    public String __response() {
        return "SuperModel";
    }
    
    public abstract JsonObject getJsonObject();
}
