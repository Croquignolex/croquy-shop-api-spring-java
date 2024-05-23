package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.media.CategoryBanner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryBannerRepository extends JpaRepository<CategoryBanner, String> {
}
