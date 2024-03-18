package com.shop.croquy.v1.services.interfaces;

import com.shop.croquy.v1.models.Media;
import com.shop.croquy.v1.models.Product;
import com.shop.croquy.v1.models.Tag;

import java.util.List;

public interface IGroupService {
    Media getBannerById(String id);
    List<Product> getProductsById(String id);
    List<Tag> getTagsById(String id);
}
