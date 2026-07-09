package com.jeevan.foodhub.repository;

import com.jeevan.foodhub.entity.Address;
import com.jeevan.foodhub.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AddressRepository extends JpaRepository<Address, Long> {

    List<Address> findByUser(User user);
    Optional<Address> findByUserAndIsDefaultTrue(User user);
    List<Address> findByUserOrderByIsDefaultDescIdDesc(User user);
}