package com.example.true_home.dto;

import lombok.Data;

import java.util.List;

@Data
public class AddressResponseDto {
    private List<PredictionDto> predictions;
    private String status;
}
