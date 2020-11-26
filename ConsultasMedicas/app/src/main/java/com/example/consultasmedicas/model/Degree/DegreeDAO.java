package com.example.consultasmedicas.model.Degree;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DegreeDAO {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("institution")
    @Expose
    private String institution;
    @SerializedName("degree")
    @Expose
    private String degree;
    @SerializedName("startYear")
    @Expose
    private String startYear;
    @SerializedName("endYear")
    @Expose
    private String endYear;
    @SerializedName("specialty")
    @Expose
    private String specialty;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getInstitution() {
        return institution;
    }

    public void setInstitution(String institution) {
        this.institution = institution;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
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

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }


}
