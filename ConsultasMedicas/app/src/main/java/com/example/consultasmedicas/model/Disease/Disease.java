package com.example.consultasmedicas.model.Disease;

public class Disease {

    private int id;
    private String name;
    private String description;

    public Disease(int id, String name, String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }

    public Disease(String name, String description){
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

}
