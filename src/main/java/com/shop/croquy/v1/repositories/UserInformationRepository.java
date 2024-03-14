package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.models.UserInformation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserInformationRepository extends JpaRepository<UserInformation, String> {
}
