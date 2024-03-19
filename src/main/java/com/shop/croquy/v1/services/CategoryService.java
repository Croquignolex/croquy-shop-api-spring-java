package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.enums.MediaType;
import com.shop.croquy.v1.enums.TaggableMorphType;
import com.shop.croquy.v1.models.Media;
import com.shop.croquy.v1.models.Tag;
import com.shop.croquy.v1.models.Taggable;
import com.shop.croquy.v1.repositories.TaggableRepository;
import com.shop.croquy.v1.repositories.MediaRepository;
import com.shop.croquy.v1.services.interfaces.ICategoryService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class CategoryService implements ICategoryService {
    private final MediaRepository mediaRepository;
    private final TaggableRepository taggableRepository;

    @Override
    public Media getBannerById(String id) {
         return mediaRepository
                 .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.CATEGORY, MediaType.BANNER)
                 .orElse(null);
    }

//    @Override
//    public List<Tag> getTagsById(String id) {
//        List<Taggable> taggable = taggableRepository.findByTaggableMorphIdAndTaggableMorphType(id, TaggableMorphType.CATEGORY);
//
//        return taggable.stream().map(Taggable::getTag).toList();
//    }
}