package com.example.consultasmedicas.model;

public class Patient {
    private String password;
    private String firstName,lastName,email,genre,phone,address,city,birthDate,birthCountry,ci,weight,height;

    public Patient(String password, String firstName, String lastName, String email, String genre, String phone, String address, String city, String birthDate, String birthCountry, String ci, String weight, String height) {
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.genre = genre;
        this.phone = phone;
        this.address = address;
        this.city = city;
        this.birthDate = birthDate;
        this.birthCountry = birthCountry;
        this.ci = ci;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getBirthCountry() {
        return birthCountry;
    }

    public void setBirthCountry(String birthCountry) {
        this.birthCountry = birthCountry;
    }

    public String getCi() {
        return ci;
    }

    public void setCi(String ci) {
        this.ci = ci;
    }
}
