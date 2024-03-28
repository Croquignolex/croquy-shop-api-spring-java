package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product_attribute")
//@Table(name = "product_attribute", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "attribute_id", "attribute_value_id"}))
public class ProductAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

//    @ManyToOne
//    @JoinColumn(name = "product_id")
//    private Product product;
//
//    @ManyToOne
//    @JoinColumn(name = "attribute_value_id")
//    private AttributeValue attributeValue;
//
//    @ManyToOne
//    @JoinColumn(name = "attribute_id")
//    private Attribute attribute;
}
