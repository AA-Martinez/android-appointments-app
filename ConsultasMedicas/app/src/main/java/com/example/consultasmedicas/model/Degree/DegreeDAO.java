package com.example.consultasmedicas.model.Degree;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DegreeDAO {

    @SerializedName("specialty")
    @Expose
    private String specialty;

    public String getSpecialty() {
        return specialty;
    }

    public void setSpecialty(String specialty) {
        this.specialty = specialty;
    }

}
