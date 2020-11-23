
package com.example.consultasmedicas.model.Diagnosis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Specialisation {

    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("SpecialistID")
    @Expose
    private int specialistID;

    public int getID() {
        return iD;
    }

    public void setID(int iD) {
        this.iD = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSpecialistID() {
        return specialistID;
    }

    public void setSpecialistID(int specialistID) {
        this.specialistID = specialistID;
    }

    public String toString() {
        return getName();
    }

}
