package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.enums.AddressMorphType;
import com.shop.croquy.v1.models.Address;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AddressRepository extends JpaRepository<Address, String> {
    Optional<Address> findByAddressMorphIdAndAddressMorphType(String addressMorphId, AddressMorphType addressMorphType);
    List<Address> findAllByAddressMorphIdAndAddressMorphType(String addressMorphId, AddressMorphType addressMorphType);
}
