package com.manoj.autonest.dto;

import java.time.LocalDate;

public class UserDto {

    private Long id;
    private String email;
    private String password;
    private String role;
    private String surname;
    private String givenname;
    private LocalDate dob;
    private String mobno;
    private String gender;
    private String city;
    private String state;
    private String country;
    private String pincode;

    public UserDto(Long id, String email, String password, String role, String surname, String givenname, 
                   LocalDate dob, String mobno, String gender, String city, String state, String country, 
                   String pincode) {
    	super();
        this.id = id;
        this.email = email;
        this.password = password;
        this.role = role;
        this.surname = surname;
        this.givenname = givenname;
        this.dob = dob;
        this.mobno = mobno;
        this.gender = gender;
        this.city = city;
        this.state = state;
        this.country = country;
        this.pincode = pincode;
    }

    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getGivenname() {
        return givenname;
    }

    public void setGivenname(String givenname) {
        this.givenname = givenname;
    }

    public LocalDate getDob() {
        return dob;
    }

    public void setDob(LocalDate dob) {
        this.dob = dob;
    }

    public String getMobno() {
        return mobno;
    }

    public void setMobno(String mobno) {
        this.mobno = mobno;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getPincode() {
        return pincode;
    }

    public void setPincode(String pincode) {
        this.pincode = pincode;
    }
}
