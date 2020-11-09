package com.example.consultasmedicas.model.Patient;

import com.example.consultasmedicas.model.AppUser.AppUserDto;

public class Patient extends AppUserDto {
    String weight,height;

    public Patient(String weight, String height) {
        this.weight = weight;
        this.height = height;
    }

    public Patient(String password, String firstName, String lastName, String email, String genre, String phone, String address, String city, String birthDate, String birthCountry, String ci, String weight, String height) {
        super(password, firstName, lastName, email, genre, phone, address, city, birthDate, birthCountry, ci);
        this.weight = weight;
        this.height = height;
    }

    public Patient() {
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

}
