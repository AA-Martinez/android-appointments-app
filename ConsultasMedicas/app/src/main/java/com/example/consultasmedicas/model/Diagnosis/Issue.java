
package com.example.consultasmedicas.model.Diagnosis;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Issue {

    @SerializedName("ID")
    @Expose
    private int iD;
    @SerializedName("Name")
    @Expose
    private String name;
    @SerializedName("Accuracy")
    @Expose
    private double accuracy;
    @SerializedName("Icd")
    @Expose
    private String icd;
    @SerializedName("IcdName")
    @Expose
    private String icdName;
    @SerializedName("ProfName")
    @Expose
    private String profName;
    @SerializedName("Ranking")
    @Expose
    private int ranking;

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

    public double getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }

    public String getIcd() {
        return icd;
    }

    public void setIcd(String icd) {
        this.icd = icd;
    }

    public String getIcdName() {
        return icdName;
    }

    public void setIcdName(String icdName) {
        this.icdName = icdName;
    }

    public String getProfName() {
        return profName;
    }

    public void setProfName(String profName) {
        this.profName = profName;
    }

    public int getRanking() {
        return ranking;
    }

    public void setRanking(int ranking) {
        this.ranking = ranking;
    }

}
