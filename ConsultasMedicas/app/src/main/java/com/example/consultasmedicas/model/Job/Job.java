package com.example.consultasmedicas.model.Job;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Job {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("workplace")
    @Expose
    private String workplace;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("startYear")
    @Expose
    private String startYear;
    @SerializedName("endYear")
    @Expose
    private String endYear;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWorkplace() {
        return workplace;
    }

    public void setWorkplace(String workplace) {
        this.workplace = workplace;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }
}
