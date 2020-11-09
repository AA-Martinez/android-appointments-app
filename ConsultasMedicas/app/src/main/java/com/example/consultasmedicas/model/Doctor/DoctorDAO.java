
package com.example.consultasmedicas.model.Doctor;

import java.util.List;

import com.example.consultasmedicas.model.AppUser.AppUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class DoctorDAO {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("appUser")
    @Expose
    private AppUser appUser;
    @SerializedName("appointments")
    @Expose
    private List<Object> appointments = null;
    @SerializedName("degrees")
    @Expose
    private List<Object> degrees = null;
    @SerializedName("jobs")
    @Expose
    private List<Object> jobs = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AppUser getAppUser() {
        return appUser;
    }

    public void setAppUser(AppUser appUser) {
        this.appUser = appUser;
    }

    public List<Object> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<Object> appointments) {
        this.appointments = appointments;
    }

    public List<Object> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<Object> degrees) {
        this.degrees = degrees;
    }

    public List<Object> getJobs() {
        return jobs;
    }

    public void setJobs(List<Object> jobs) {
        this.jobs = jobs;
    }

}
