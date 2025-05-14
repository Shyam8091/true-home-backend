package com.example.true_home.dto;

import lombok.Data;

import java.util.List;
import java.util.StringJoiner;

@Data
public class ListingDto {
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
    private String ownerId;

    private List<String> images;
    private double area;
    private String apartmentType;


    @Override
    public String toString() {
        return new StringJoiner(", ", ListingDto.class.getSimpleName() + "{", "}")
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
                .add("\"images\":" + images)
                .add("\"ownerId\":" + ownerId)
                .add("\"area\":" + area)
                .add("\"apartmentType\":" + apartmentType)
                .toString();
    }
}

