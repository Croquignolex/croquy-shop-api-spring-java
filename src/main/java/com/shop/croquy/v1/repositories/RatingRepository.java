package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.enums.RatingMorphType;
import com.shop.croquy.v1.entities.Rating;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, String> {
    List<Rating> findAllByRatingMorphIdAndRatingMorphType(String ratingMorphId, RatingMorphType ratingMorphType);
}
