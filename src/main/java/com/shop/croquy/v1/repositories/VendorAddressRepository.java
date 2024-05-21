package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.address.VendorAddress;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VendorAddressRepository extends JpaRepository<VendorAddress, String> {
}
