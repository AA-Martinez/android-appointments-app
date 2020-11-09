package com.example.consultasmedicas.model.Doctor;

import com.example.consultasmedicas.model.AppUser.AppUserDto;
import com.example.consultasmedicas.model.Appointment.Appointment;
import com.example.consultasmedicas.model.Degree.Degree;
import com.example.consultasmedicas.model.Job.Job;

import java.util.Set;

public class Doctor extends AppUserDto {

    Set<Appointment> appointments;
    Set<Degree> degrees;
    Set<Job> jobs;

    public Doctor(Set<Appointment> appointments, Set<Degree> degrees, Set<Job> jobs) {
        this.appointments = appointments;
        this.degrees = degrees;
        this.jobs = jobs;
    }

    public Doctor(String username, String password, Set<Appointment> appointments, Set<Degree> degrees, Set<Job> jobs) {
        super(username, password);
        this.appointments = appointments;
        this.degrees = degrees;
        this.jobs = jobs;
    }

    public Doctor(String password, String firstName, String lastName, String email, String genre, String phone, String address, String city, String birthDate, String birthCountry, String ci, Set<Appointment> appointments, Set<Degree> degrees, Set<Job> jobs) {
        super(password, firstName, lastName, email, genre, phone, address, city, birthDate, birthCountry, ci);
        this.appointments = appointments;
        this.degrees = degrees;
        this.jobs = jobs;
    }

    public Set<Appointment> getAppointments() {
        return appointments;
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments = appointments;
    }

    public Set<Degree> getDegrees() {
        return degrees;
    }

    public void setDegrees(Set<Degree> degrees) {
        this.degrees = degrees;
    }

    public Set<Job> getJobs() {
        return jobs;
    }

    public void setJobs(Set<Job> jobs) {
        this.jobs = jobs;
    }
}
