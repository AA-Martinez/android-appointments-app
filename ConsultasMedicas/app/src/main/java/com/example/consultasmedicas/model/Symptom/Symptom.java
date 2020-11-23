package com.example.consultasmedicas.model.Symptom;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Symptom implements Serializable{
    @SerializedName("ID")
    @Expose
    private int ID;
    @SerializedName("Name")
    @Expose
    private String name;

    public int getID() {
        return ID;
    }

    public void setID(int iD) {
        this.ID = iD;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return getName();
    }

}
