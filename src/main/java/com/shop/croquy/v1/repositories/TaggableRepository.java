package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.enums.TaggableMorphType;
import com.shop.croquy.v1.models.Taggable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaggableRepository extends JpaRepository<Taggable, String> {
    List<Taggable> findByTaggableMorphIdAndTaggableMorphType(String taggableMorphId, TaggableMorphType taggableMorphType);
}
