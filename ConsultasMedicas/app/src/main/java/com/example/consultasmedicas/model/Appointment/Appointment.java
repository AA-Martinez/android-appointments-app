package com.example.consultasmedicas.model.Appointment;

public class Appointment {

    String message;
    String patientId;

    public Appointment() {
    }

    public Appointment(String message, String patientId) {
        this.message = message;
        this.patientId = patientId;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPatientId() {
        return patientId;
    }

    public void setPatientId(String patientId) {
        this.patientId = patientId;
    }
}
