package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Vendor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface VendorRepository extends JpaRepository<Vendor, String> {
    Optional<Vendor> findFistByName(String name);
    List<Vendor> findByNameContains(String name);
    List<Vendor> findByEnabledOrderByName(boolean enabled);
    Optional<Vendor> findFistByNameAndIdNot(String name, String id);
}
