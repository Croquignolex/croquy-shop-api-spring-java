package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.ProductAttribute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductAttributeRepository extends JpaRepository<ProductAttribute, String> {
//    List<ProductAttribute> findAllByProductId(String productId);
//    List<ProductAttribute> findAllByProductIdAndAttributeId(String productId, String attributeId);
}
