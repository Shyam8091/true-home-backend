package com.example.true_home.entity;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "T_USERS")
public class User extends CommonEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID")
    private int id;

    @Column(name = "FIRSTNAME")
    private String firstname;

    @Column(name = "LASTNAME")
    private String lastname;

    @Column(name = "COUNTRY")
    private String country;

    @Column(name = "EMAIL")
    private String email;

    @Column(name = "MOBILE")
    private String mobile;

    @Column(name = "CITY")
    private String city;

    @Column(name = "STATE")
    private String state;

    @Column(name = "PINCODE")
    private String pinCode;


    @Column(name = "IS_ACTIVE", insertable = false)
    private Boolean isActive;

    @OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
    private List<Listing> listings;

    public User() {
    }
    public User(int id){
        this.id=id;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFirstname() {
        return this.firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public Boolean getIsActive() {
        return this.isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public String getLastname() {
        return this.lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }


    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getPinCode() {
        return pinCode;
    }

    public void setPinCode(String pinCode) {
        this.pinCode = pinCode;
    }


    @Override
    public String toString() {
        return new StringJoiner(", ", User.class.getSimpleName() + "{", "}")
                .add("\"id\":" + id)
                .add("\"email\":\"" + email + "\"")
                .add("\"mobile\":\"" + mobile + "\"")
                .add("\"firstname\":\"" + firstname + "\"")
                .add("\"lastname\":\"" + lastname + "\"")
                .add("\"country\":\"" + country + "\"")
                .add("\"state\":\"" + state + "\"")
                .add("\"city\":\"" + city + "\"")
                .add("\"pinCode\":\"" + pinCode + "\"")
                .add("\"state\":\"" + state + "\"")
                .add("\"isActive\":" + isActive)
                .toString();
    }
}

