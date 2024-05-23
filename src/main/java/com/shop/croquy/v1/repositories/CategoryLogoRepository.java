package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.media.CategoryLogo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryLogoRepository extends JpaRepository<CategoryLogo, String> {
}
