package com.example.true_home.dto;

import lombok.Data;

@Data
public class ResultDto {
    private String formatted_address;
    private GeometryDto geometry;
}
