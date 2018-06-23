package com.upp.reverseauction.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;

public class RegistrationDetails {

    private String name;
    private String surname;
    private String email;
    private String username;
    private String password;
    private String address;
    private String place;
    private String zipCode;
    private boolean isCompany;
    private String lat;
    private String lng;

    public RegistrationDetails() {
    }

    public RegistrationDetails(String name, String surname, String email, String username, String password, String address, String place, String zipCode, boolean isCompany, String lat, String lng) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.username = username;
        this.password = password;
        this.address = address;
        this.place = place;
        this.zipCode = zipCode;
        this.isCompany = isCompany;
        this.lat = lat;
        this.lng = lng;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    @JsonIgnore
    public boolean isCompany() {
        return isCompany;
    }

    public String getIsCompany() {
        return String.valueOf(isCompany);
    }

    public void setCompany(boolean company) {
        isCompany = company;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }
}
