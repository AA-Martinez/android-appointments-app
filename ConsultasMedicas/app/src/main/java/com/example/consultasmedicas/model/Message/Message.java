package com.example.consultasmedicas.model.Message;

import com.google.firebase.firestore.ServerTimestamp;

import java.util.Date;

public class Message {

    private int appUserId;
    private String text;
    private int appointmentId;
    @ServerTimestamp
    private Date creationTimeStamp;

    public int getAppUserId() {
        return appUserId;
    }

    public void setAppUserId(int appUserId) {
        this.appUserId = appUserId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public void setAppointmentId(int appointmentId) {
        this.appointmentId = appointmentId;
    }

    public Date getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Date creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }
}
