package com.example.true_home.service.impl;

import com.example.true_home.dto.AddressResponseDto;
import com.example.true_home.dto.PlaceDetailsDto;
import com.example.true_home.feign.GooglePlacesClient;
import com.example.true_home.service.AddressService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.RestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AddressServiceImpl implements AddressService {

    @Value("${google.api.key}")
    private String googleApiKey;

    @Autowired
    private GooglePlacesClient googlePlacesClient;

    @Override
    public ResponseEntity<RestResponse<AddressResponseDto>> getAddressSuggestion(String input) {
        AddressResponseDto autocomplete = googlePlacesClient.getAutocomplete(input, googleApiKey);
        return RestUtils.successResponse(autocomplete, HttpStatus.OK, "AutoComplete Address fetched successfully");
    }


    public PlaceDetailsDto getPlaceDetails(String placeId) {
        return googlePlacesClient.getPlaceDetails(placeId, googleApiKey);
    }
}
