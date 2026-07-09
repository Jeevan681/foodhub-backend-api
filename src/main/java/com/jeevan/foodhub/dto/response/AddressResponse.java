package com.jeevan.foodhub.dto.response;

import com.jeevan.foodhub.entity.AddressType;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AddressResponse {

    private Long id;

    private Long userId;

    private String fullName;

    private String phone;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String state;

    private String postalCode;

    private String country;

    private AddressType type;

    private Boolean isDefault;
}