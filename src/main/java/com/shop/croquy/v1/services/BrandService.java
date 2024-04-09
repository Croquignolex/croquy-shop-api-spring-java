package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.enums.MediaType;
import com.shop.croquy.v1.entities.Media;
import com.shop.croquy.v1.repositories.MediaRepository;
import com.shop.croquy.v1.repositories.TaggableRepository;
import com.shop.croquy.v1.services.interfaces.IBrandService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class BrandService implements IBrandService {
    private final MediaRepository mediaRepository;
    private final TaggableRepository taggableRepository;

    @Override
    public Media getLogoById(String id) {
         /*return mediaRepository
                 .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.BRAND, MediaType.LOGO)
                 .orElse(null);*/
        return null;
    }

//    @Override
//    public List<Tag> getTagsById(String id) {
//        List<Taggable> taggable = taggableRepository.findByTaggableMorphIdAndTaggableMorphType(id, TaggableMorphType.BRAND);
//
//        return taggable.stream().map(Taggable::getTag).toList();
//    }
}