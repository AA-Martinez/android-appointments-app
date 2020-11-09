
package com.example.consultasmedicas.model.Patient;

import java.util.List;

import com.example.consultasmedicas.model.AppUser.AppUser;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PatientDAO {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("weight")
    @Expose
    private String weight;
    @SerializedName("height")
    @Expose
    private String height;
    @SerializedName("creationTimeStamp")
    @Expose
    private Object creationTimeStamp;
    @SerializedName("appUser")
    @Expose
    private AppUser appUser;
    @SerializedName("appointments")
    @Expose
    private List<Object> appointments = null;
    @SerializedName("diseases")
    @Expose
    private List<Object> diseases = null;
    @SerializedName("allergies")
    @Expose
    private List<Object> allergies = null;
    @SerializedName("medications")
    @Expose
    private List<Object> medications = null;
    @SerializedName("substances")
    @Expose
    private List<Object> substances = null;
    @SerializedName("googleCalendars")
    @Expose
    private List<Object> googleCalendars = null;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String getHeight() {
        return height;
    }

    public void setHeight(String height) {
        this.height = height;
    }

    public Object getCreationTimeStamp() {
        return creationTimeStamp;
    }

    public void setCreationTimeStamp(Object creationTimeStamp) {
        this.creationTimeStamp = creationTimeStamp;
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

    public List<Object> getDiseases() {
        return diseases;
    }

    public void setDiseases(List<Object> diseases) {
        this.diseases = diseases;
    }

    public List<Object> getAllergies() {
        return allergies;
    }

    public void setAllergies(List<Object> allergies) {
        this.allergies = allergies;
    }

    public List<Object> getMedications() {
        return medications;
    }

    public void setMedications(List<Object> medications) {
        this.medications = medications;
    }

    public List<Object> getSubstances() {
        return substances;
    }

    public void setSubstances(List<Object> substances) {
        this.substances = substances;
    }

    public List<Object> getGoogleCalendars() {
        return googleCalendars;
    }

    public void setGoogleCalendars(List<Object> googleCalendars) {
        this.googleCalendars = googleCalendars;
    }

}
