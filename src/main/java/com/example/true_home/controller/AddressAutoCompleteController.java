package com.example.true_home.controller;

import com.example.true_home.dto.AddressResponseDto;
import com.example.true_home.service.AddressService;
import com.example.true_home.util.RestResponse;
import com.example.true_home.util.TrueHomeConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = TrueHomeConstants.ROOT)
public class AddressAutoCompleteController {

    @Autowired
    private AddressService addressService;

    @GetMapping(TrueHomeConstants.ADDRESS_AUTO_COMPLETE)
    public ResponseEntity<RestResponse<AddressResponseDto>> getAddressSuggestion(@RequestParam String input) {
        ResponseEntity<RestResponse<AddressResponseDto>> restResponseResponseEntity = addressService.getAddressSuggestion(input);
        return restResponseResponseEntity;
    }

}
