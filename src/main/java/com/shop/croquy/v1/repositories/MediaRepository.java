package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.models.Media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
    Optional<Media> findByName(String name);
    Optional<Media> findByMediaMorphIdAndMediaMorphType(String mediaMorphId, MediaMorphType mediaMorphType);
    List<Media> findAllByMediaMorphIdAndMediaMorphType(String mediaMorphId, MediaMorphType mediaMorphType);
}
