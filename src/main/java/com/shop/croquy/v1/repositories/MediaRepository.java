package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.enums.MediaType;
import com.shop.croquy.v1.entities.Media;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MediaRepository extends JpaRepository<Media, String> {
    Optional<Media> findByName(String name);
//    Optional<Media> findByMediaMorphIdAndMediaMorphTypeAndType(String mediaMorphId, MediaMorphType mediaMorphType, MediaType type);
//    List<Media> findAllByMediaMorphIdAndMediaMorphTypeAndType(String mediaMorphId, MediaMorphType mediaMorphType, MediaType type);
}
