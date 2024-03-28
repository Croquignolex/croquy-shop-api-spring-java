package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.entities.Media;
import com.shop.croquy.v1.entities.Rating;

import java.util.List;

public interface IProductService {
    List<Rating> getRatingsById(String id);
    Media getAboutNoticeById(String id);
    Media getPresentationVideoById(String id);
    List<Media> getGalleryImagesById(String id);
    Integer getNoteById(String id);
//    List<Attribute> getAttributesById(String id);
//    List<Tag> getTagsById(String id);
}
