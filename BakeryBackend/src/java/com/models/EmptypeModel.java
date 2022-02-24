package com.models;

import java.sql.ResultSet;
import java.sql.Timestamp;
import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonObjectBuilder;

public class EmptypeModel extends SuperModel {

    private long emptypeid;
    private String name;
    private String notes;
    public static final String sequence_id = "emptypes_emptypeid_seq";

    public EmptypeModel(long emptypeid, String name, String notes) {
        this.emptypeid = emptypeid;
        this.name = name;
        this.notes = notes;
    }

    public EmptypeModel(String name, String notes) {
        this.name = name;
        this.notes = notes;
    }

    public EmptypeModel(ResultSet set) throws Exception {
        this.emptypeid = set.getLong("emptypeid");
        this.name = set.getString("name");
        this.notes = set.getString("notes");
    }

    @Override
    public JsonObject getJsonObject() {
        JsonObjectBuilder json = null;

        json = Json.createObjectBuilder()
                .add("emptypeid", emptypeid)
                .add("name", name)
                .add("notes", notes);

        return json.build();
    }

    public EmptypeModel() {
    }

    public long getEmptypeid() {
        return emptypeid;
    }

    public void setEmptypeid(long emptypeid) {
        this.emptypeid = emptypeid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

}
