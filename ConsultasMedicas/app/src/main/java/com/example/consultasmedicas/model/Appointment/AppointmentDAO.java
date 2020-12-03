package com.example.consultasmedicas.model.Appointment;

import java.util.List;

import com.example.consultasmedicas.model.Doctor.DoctorDAO;
import com.example.consultasmedicas.model.Message.MessageDAO;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AppointmentDAO {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("creationTimeStamp")
    @Expose
    private String creationTimeStamp;
    @SerializedName("closeTimeStamp")
    @Expose
    private Object closeTimeStamp;
    @SerializedName("lastUpdateTimeStamp")
    @Expose
    private String lastUpdateTimeStamp;
    @SerializedName("status")
    @Expose
    private int status;
    @SerializedName("messages")
    @Expose
    private List<MessageDAO> messages;
    @SerializedName("treatments")
    @Expose
    private List<Object> treatments = null;
    @SerializedName("doctors")
    @Expose
    private List<DoctorDAO> doctors;

    public List<DoctorDAO> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<DoctorDAO> doctors) {
        this.doctors = doctors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(String creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
    }

    public Object getCloseTimeStamp() {
        return closeTimeStamp;
    }

    public void setCloseTimeStamp(Object closeTimeStamp) {
        this.closeTimeStamp = closeTimeStamp;
    }

    public String getLastUpdateTimeStamp() {
        return lastUpdateTimeStamp;
    }

    public void setLastUpdateTimeStamp(String lastUpdateTimeStamp) {
        this.lastUpdateTimeStamp = lastUpdateTimeStamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public List<MessageDAO> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageDAO> messages) {
        this.messages = messages;
    }

    public List<Object> getTreatments() {
        return treatments;
    }

    public void setTreatments(List<Object> treatments) {
        this.treatments = treatments;
    }

}