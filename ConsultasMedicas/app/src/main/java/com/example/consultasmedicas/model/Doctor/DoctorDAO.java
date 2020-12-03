
package com.example.consultasmedicas.model.Doctor;

import java.util.List;

import com.example.consultasmedicas.model.AppUser.AppUser;
import com.example.consultasmedicas.model.Appointment.AppointmentDAO;
import com.example.consultasmedicas.model.Degree.DegreeDAO;
import com.example.consultasmedicas.model.Job.Job;
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
    private List<AppointmentDAO> appointments;
    @SerializedName("degrees")
    @Expose
    private List<DegreeDAO> degrees;
    @SerializedName("jobs")
    @Expose
    private List<Job> jobs;

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

    public List<AppointmentDAO> getAppointments() {
        return appointments;
    }

    public void setAppointments(List<AppointmentDAO> appointments) {
        this.appointments = appointments;
    }

    public List<DegreeDAO> getDegrees() {
        return degrees;
    }

    public void setDegrees(List<DegreeDAO> degrees) {
        this.degrees = degrees;
    }

    public List<Job> getJobs() {
        return jobs;
    }

    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }

}
