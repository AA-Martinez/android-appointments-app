package com.example.consultasmedicas.model.Diagnosis;

public class Diagnosis {
    String name;
    double Accuracy;
    String cause;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAccuracy() {
        return Accuracy;
    }

    public void setAccuracy(double accuracy) {
        Accuracy = accuracy;
    }

    public String getCause() {
        return cause;
    }

    public void setCause(String cause) {
        this.cause = cause;
    }
}
