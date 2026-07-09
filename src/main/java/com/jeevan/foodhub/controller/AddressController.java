package com.jeevan.foodhub.controller;

import com.jeevan.foodhub.dto.request.CreateAddressRequest;
import com.jeevan.foodhub.dto.request.UpdateAddressRequest;
import com.jeevan.foodhub.dto.response.AddressResponse;
import com.jeevan.foodhub.service.AddressService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/addresses")
public class AddressController {

    private final AddressService addressService;

    public AddressController(AddressService addressService) {
        this.addressService = addressService;
    }

    @PostMapping
    public ResponseEntity<AddressResponse> createAddress(

            Authentication authentication,

            @Valid
            @RequestBody
            CreateAddressRequest request) {

        AddressResponse response =
                addressService.createAddress(
                        authentication.getName(),
                        request
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping
    public ResponseEntity<List<AddressResponse>> getMyAddresses(

            Authentication authentication) {

        return ResponseEntity.ok(
                addressService.getMyAddresses(
                        authentication.getName()
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponse> getAddressById(

            Authentication authentication,

            @PathVariable Long id) {

        return ResponseEntity.ok(
                addressService.getAddressById(
                        authentication.getName(),
                        id
                )
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponse> updateAddress(

            Authentication authentication,

            @PathVariable Long id,

            @Valid
            @RequestBody
            UpdateAddressRequest request) {

        return ResponseEntity.ok(
                addressService.updateAddress(
                        authentication.getName(),
                        id,
                        request
                )
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteAddress(

            Authentication authentication,

            @PathVariable Long id) {

        addressService.deleteAddress(
                authentication.getName(),
                id
        );

        return ResponseEntity.ok(
                "Address deleted successfully"
        );
    }
}