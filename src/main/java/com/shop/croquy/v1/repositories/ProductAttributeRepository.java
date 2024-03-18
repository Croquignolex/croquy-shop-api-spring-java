package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.models.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, String> {
    List<ProductAttribute> findAllByProductId(String productId);
    List<ProductAttribute> findAllByProductIdAndAttributeId(String productId, String attributeId);
}
