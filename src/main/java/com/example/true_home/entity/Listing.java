package com.example.true_home.entity;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@Entity
@Table(name = "T_LISTING")
@Data
public class Listing extends CommonEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "ownerId", nullable = false)
    private User owner;  // Reference to Account

    private String projectName;
    private String type;
    private String bhk;
    private int floor;
    private int totalFloor;
    private double price;
    private String description;
    private String city;
    private String postal;
    private String state;
    private double area;
    private String apartmentType;

    @OneToMany(mappedBy = "listing", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
    @Transient
    private long wishlistCount;

    @Transient
    private boolean wishListed;

    public Listing(int id) {
        this.id = id;
    }

    public Listing() {

    }

    @Override
    public String toString() {
        return new StringJoiner(", ", Listing.class.getSimpleName() + "{", "}")
                .add("\"id\":" + id)
                .add("\"owner\":" + owner)
                .add("\"projectName\":\"" + projectName + "\"")
                .add("\"type\":\"" + type + "\"")
                .add("\"bhk\":\"" + bhk + "\"")
                .add("\"flor\":" + floor)
                .add("\"totalFloor\":" + totalFloor)
                .add("\"price\":" + price)
                .add("\"description\":\"" + description + "\"")
                .add("\"city\":\"" + city + "\"")
                .add("\"postal\":\"" + postal + "\"")
                .add("\"state\":\"" + state + "\"")
                .add("\"area\":\"" + area + "\"")
                .add("\"apartmentType\":\"" + apartmentType + "\"")
                .add("\"wishlistCount\":\"" + wishlistCount + "\"")
                .toString();
    }
}
