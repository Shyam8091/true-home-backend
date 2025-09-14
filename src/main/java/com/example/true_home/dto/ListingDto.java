package com.example.true_home.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.StringJoiner;

@Data
public class ListingDto {
    private String projectName;
    private String type;
    private String bhk;

    private Integer floor;
    private Integer totalFloor;
    private Double price;
    private String description;
    private String city;
    private String postal;
    private String state;

    private String locality;
    private String ownerId;
    private List<String> amenities;
    private List<String> images;
    private Double area;
    private String apartmentType;
    private MultipartFile file;


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

