package com.shop.croquy.v1.services;

import com.shop.croquy.v1.repositories.ProductAttributeRepository;
import com.shop.croquy.v1.services.interfaces.IAttributeService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AttributeService implements IAttributeService {
    private final ProductAttributeRepository productAttributeRepository;

//    @Override
//    public List<AttributeValue> getAttributeValuesByIdAndByProductId(String id, String productId) {
//        List<ProductAttribute> productAttributes = productAttributeRepository.findAllByProductIdAndAttributeId(productId, id);
//
//        return productAttributes.stream().map(ProductAttribute::getAttributeValue).toList();
//    }
}