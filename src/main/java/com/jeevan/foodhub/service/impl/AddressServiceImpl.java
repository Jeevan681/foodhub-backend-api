package com.jeevan.foodhub.service.impl;

import com.jeevan.foodhub.dto.request.CreateAddressRequest;
import com.jeevan.foodhub.dto.request.UpdateAddressRequest;
import com.jeevan.foodhub.dto.response.AddressResponse;
import com.jeevan.foodhub.entity.Address;
import com.jeevan.foodhub.entity.User;
import com.jeevan.foodhub.exception.ResourceNotFoundException;
import com.jeevan.foodhub.mapper.AddressMapper;
import com.jeevan.foodhub.repository.AddressRepository;
import com.jeevan.foodhub.repository.UserRepository;
import com.jeevan.foodhub.service.AddressService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    private final UserRepository userRepository;
    private final AddressRepository addressRepository;

    public AddressServiceImpl(
            UserRepository userRepository,
            AddressRepository addressRepository) {

        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    @Transactional
    public AddressResponse createAddress(
            String userEmail,
            CreateAddressRequest request) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        if (Boolean.TRUE.equals(request.getIsDefault())) {

            addressRepository.findByUserAndIsDefaultTrue(user)
                    .ifPresent(address -> {
                        address.setIsDefault(false);
                        addressRepository.save(address);
                    });
        }

        Address address = Address.builder()
                .fullName(request.getFullName())
                .phone(request.getPhone())
                .addressLine1(request.getAddressLine1())
                .addressLine2(request.getAddressLine2())
                .city(request.getCity())
                .state(request.getState())
                .postalCode(request.getPostalCode())
                .country(request.getCountry())
                .type(request.getType())
                .isDefault(Boolean.TRUE.equals(request.getIsDefault()))
                .user(user)
                .build();

        return AddressMapper.toAddressResponse(
                addressRepository.save(address)
        );
    }

    @Override
    public List<AddressResponse> getMyAddresses(String userEmail) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return addressRepository
                .findByUserOrderByIsDefaultDescIdDesc(user)
                .stream()
                .map(AddressMapper::toAddressResponse)
                .toList();
    }

    @Override
    public AddressResponse getAddressById(
            String userEmail,
            Long addressId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Address does not belong to this user"
            );
        }

        return AddressMapper.toAddressResponse(address);
    }

    @Override
    @Transactional
    public AddressResponse updateAddress(
            String userEmail,
            Long addressId,
            UpdateAddressRequest request) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Address does not belong to this user"
            );
        }

        if (Boolean.TRUE.equals(request.getIsDefault())) {

            addressRepository.findByUserAndIsDefaultTrue(user)
                    .ifPresent(defaultAddress -> {
                        defaultAddress.setIsDefault(false);
                        addressRepository.save(defaultAddress);
                    });
        }

        address.setFullName(request.getFullName());
        address.setPhone(request.getPhone());
        address.setAddressLine1(request.getAddressLine1());
        address.setAddressLine2(request.getAddressLine2());
        address.setCity(request.getCity());
        address.setState(request.getState());
        address.setPostalCode(request.getPostalCode());
        address.setCountry(request.getCountry());
        address.setType(request.getType());
        address.setIsDefault(Boolean.TRUE.equals(request.getIsDefault()));

        return AddressMapper.toAddressResponse(
                addressRepository.save(address)
        );
    }

    @Override
    public void deleteAddress(
            String userEmail,
            Long addressId) {

        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        Address address = addressRepository.findById(addressId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Address not found"));

        if (!address.getUser().getId().equals(user.getId())) {
            throw new ResourceNotFoundException(
                    "Address does not belong to this user"
            );
        }

        addressRepository.delete(address);
    }
}