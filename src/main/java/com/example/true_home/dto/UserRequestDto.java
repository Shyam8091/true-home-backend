package com.example.true_home.dto;

import java.util.StringJoiner;

public class UserRequestDto {
    private String email;
    private String mobile;
    private String firstname;
    private String lastname;
    private String country;
    private String city;
    private String state;
    private String pinCode;

    public String getEmail() {
        return email;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
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

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", UserRequestDto.class.getSimpleName() + "{", "}")
                .add("\"email\":\"" + email + "\"")
                .add("\"mobile\":\"" + mobile + "\"")
                .add("\"firstname\":\"" + firstname + "\"")
                .add("\"lastname\":\"" + lastname + "\"")
                .add("\"country\":\"" + country + "\"")
                .add("\"state\":\"" + state + "\"")
                .add("\"city\":\"" + city + "\"")
                .add("\"pinCode\":\"" + pinCode + "\"")
                .toString();
    }
}
