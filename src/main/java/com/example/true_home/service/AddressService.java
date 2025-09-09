package com.example.true_home.service;

import com.example.true_home.dto.AddressResponseDto;
import com.example.true_home.util.RestResponse;
import org.springframework.http.ResponseEntity;

public interface AddressService {

    ResponseEntity<RestResponse<AddressResponseDto>> getAddressSuggestion(String input);
}
