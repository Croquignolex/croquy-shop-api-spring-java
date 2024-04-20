package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.CountryFlag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CountryFlagRepository extends JpaRepository<CountryFlag, String> {
}
