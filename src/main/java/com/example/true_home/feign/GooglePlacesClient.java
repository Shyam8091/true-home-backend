package com.example.true_home.feign;

import com.example.true_home.dto.AddressResponseDto;
import com.example.true_home.dto.PlaceDetailsDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(
        name = "googlePlacesClient",
        url = "https://maps.googleapis.com/maps/api/place"
)
public interface GooglePlacesClient {

    @GetMapping("/autocomplete/json")
    AddressResponseDto getAutocomplete(
            @RequestParam("input") String input,
            @RequestParam("key") String apiKey
    );

    @GetMapping("/details/json")
    PlaceDetailsDto getPlaceDetails(
            @RequestParam("place_id") String placeId,
            @RequestParam("key") String apiKey
    );
}
