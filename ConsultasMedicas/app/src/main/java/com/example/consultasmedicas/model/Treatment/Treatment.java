package com.example.consultasmedicas.model.Treatment;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Treatment {
    private int appointmentId;
    private String description;
    private int doctorAppUserId,
                interval,
                patientAppUserId,
                recurrence;
    @ServerTimestamp
    private Date startTimestamp;

    public Treatment() {
    }

    public Treatment(int appointmentId, String description, int doctorAppUserId, int interval, int patientAppUserId, int recurrence, Date startTimestamp) {
        this.appointmentId = appointmentId;
        this.description = description;
        this.doctorAppUserId = doctorAppUserId;
        this.interval = interval;
        this.patientAppUserId = patientAppUserId;
        this.recurrence = recurrence;
        this.startTimestamp = startTimestamp;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDoctorAppUserId() {
        return doctorAppUserId;
    }

    public void setDoctorAppUserId(int doctorAppUserId) {
        this.doctorAppUserId = doctorAppUserId;
    }

    public int getInterval() {
        return interval;
    }

    public void setInterval(int interval) {
        this.interval = interval;
    }

    public int getPatientAppUserId() {
        return patientAppUserId;
    }

    public void setPatientAppUserId(int patientAppUserId) {
        this.patientAppUserId = patientAppUserId;
    }

    public int getRecurrence() {
        return recurrence;
    }

    public void setRecurrence(int recurrence) {
        this.recurrence = recurrence;
    }

    public Date getStartTimestamp() {
        return startTimestamp;
    }

    public void setStartTimestamp(Date startTimestamp) {
        this.startTimestamp = startTimestamp;
    }
}
