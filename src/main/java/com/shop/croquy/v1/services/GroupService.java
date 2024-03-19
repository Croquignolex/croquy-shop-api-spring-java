package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.enums.MediaType;
import com.shop.croquy.v1.enums.TaggableMorphType;
import com.shop.croquy.v1.models.*;
import com.shop.croquy.v1.repositories.MediaRepository;
import com.shop.croquy.v1.repositories.CategoryRepository;
import com.shop.croquy.v1.repositories.TaggableRepository;
import com.shop.croquy.v1.services.interfaces.IGroupService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService implements IGroupService {
    private final MediaRepository mediaRepository;
    private final CategoryRepository categoryRepository;
    private final TaggableRepository taggableRepository;

    @Override
    public Media getBannerById(String id) {
         return mediaRepository
                 .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.GROUP, MediaType.BANNER)
                 .orElse(null);
    }

//    @Override
//    public List<Product> getProductsById(String id) {
//        List<Category> categories = categoryRepository.findAllByGroupId(id);
//        List<Product> products = new ArrayList<>();
//
//        categories.forEach((Category category) -> products.addAll(category.getProducts()));
//
//        return products;
//    }
//
//    @Override
//    public List<Tag> getTagsById(String id) {
//        List<Taggable> taggable = taggableRepository.findByTaggableMorphIdAndTaggableMorphType(id, TaggableMorphType.GROUP);
//
//        return taggable.stream().map(Taggable::getTag).toList();
//    }
}