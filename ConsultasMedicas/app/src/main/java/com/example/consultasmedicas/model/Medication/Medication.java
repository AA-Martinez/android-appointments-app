package com.example.consultasmedicas.model.Medication;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Medication {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("description")
    @Expose
    private String description;

    public Medication(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Medication(String name, String description){
        this.name = name;
        this.description = description;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    @Override
    public String toString() {
        return getName();
    }

}
