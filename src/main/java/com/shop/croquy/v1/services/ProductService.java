package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.enums.MediaType;
import com.shop.croquy.v1.enums.RatingMorphType;
import com.shop.croquy.v1.enums.TaggableMorphType;
import com.shop.croquy.v1.models.*;
import com.shop.croquy.v1.repositories.ProductAttributeRepository;
import com.shop.croquy.v1.repositories.TaggableRepository;
import com.shop.croquy.v1.repositories.RatingRepository;
import com.shop.croquy.v1.repositories.MediaRepository;
import com.shop.croquy.v1.services.interfaces.IProductService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.IntSummaryStatistics;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ProductService implements IProductService {
    private final RatingRepository ratingRepository;
    private final MediaRepository mediaRepository;
    private final ProductAttributeRepository productAttributeRepository;
    private final TaggableRepository taggableRepository;

    @Override
    public List<Rating> getRatingsById(String id) {
         return ratingRepository.findAllByRatingMorphIdAndRatingMorphType(id, RatingMorphType.PRODUCT);
    }

    @Override
    public Media getAboutNoticeById(String id) {
        return mediaRepository
                .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.PRODUCT, MediaType.TXT)
                .orElse(null);
    }

    @Override
    public Media getPresentationVideoById(String id) {
        return mediaRepository
                .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.PRODUCT, MediaType.VIDEO)
                .orElse(null);
    }

    @Override
    public List<Media> getGalleryImagesById(String id) {
        return mediaRepository.findAllByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.PRODUCT, MediaType.IMAGE);
    }

    @Override
    public Integer getNoteById(String id) {
        IntSummaryStatistics intSummaryStatistics = getRatingsById(id).stream().mapToInt(Rating::getNote).summaryStatistics();

        return (int)intSummaryStatistics.getAverage();
    }

    @Override
    public List<Attribute> getAttributesById(String id) {
        List<ProductAttribute> productAttributes = productAttributeRepository.findAllByProductId(id);

        return productAttributes.stream().map(ProductAttribute::getAttribute).toList();
    }

    @Override
    public List<Tag> getTagsById(String id) {
        List<Taggable> taggable = taggableRepository.findByTaggableMorphIdAndTaggableMorphType(id, TaggableMorphType.PRODUCT);

        return taggable.stream().map(Taggable::getTag).toList();
    }
}