package com.example.true_home.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@RequiredArgsConstructor
@Builder
public class ListingResponseDto {

    private int id;
    private String projectName;
    private String type;
    private String city;
    private String bhk;
    private double price;
    private String description;
    private String postal;
    private int floor;
    private int totalFloor;
    private String ownerId;
    private List<String> images;  // Base64 encoded images
    private double area;
    private String apartmentType;
    private long wishlistCount;
    private boolean wishListed;
    private Long wishListId;

}