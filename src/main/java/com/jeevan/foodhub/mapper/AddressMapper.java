package com.jeevan.foodhub.mapper;

import com.jeevan.foodhub.dto.response.AddressResponse;
import com.jeevan.foodhub.entity.Address;

public class AddressMapper {

    private AddressMapper() {
    }

    public static AddressResponse toAddressResponse(Address address) {

        return AddressResponse.builder()
                .id(address.getId())
                .userId(address.getUser().getId())
                .fullName(address.getFullName())
                .phone(address.getPhone())
                .addressLine1(address.getAddressLine1())
                .addressLine2(address.getAddressLine2())
                .city(address.getCity())
                .state(address.getState())
                .postalCode(address.getPostalCode())
                .country(address.getCountry())
                .type(address.getType())
                .isDefault(address.getIsDefault())
                .build();
    }
}