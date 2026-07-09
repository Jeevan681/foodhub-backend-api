package com.jeevan.foodhub.service;

import com.jeevan.foodhub.dto.request.CreateAddressRequest;
import com.jeevan.foodhub.dto.request.UpdateAddressRequest;
import com.jeevan.foodhub.dto.response.AddressResponse;

import java.util.List;

public interface AddressService {

    AddressResponse createAddress(
            String userEmail,
            CreateAddressRequest request
    );

    List<AddressResponse> getMyAddresses(
            String userEmail
    );

    AddressResponse getAddressById(
            String userEmail,
            Long addressId
    );

    AddressResponse updateAddress(
            String userEmail,
            Long addressId,
            UpdateAddressRequest request
    );

    void deleteAddress(
            String userEmail,
            Long addressId
    );
}