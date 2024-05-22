package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.media.GroupBanner;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupBannerRepository extends JpaRepository<GroupBanner, String> {
}
