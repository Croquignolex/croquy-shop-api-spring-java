package com.shop.croquy.v1.models;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "product_attribute", uniqueConstraints = @UniqueConstraint(columnNames = {"product_id", "attribute_id", "attribute_value_id"}))
public class productAttribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="product_id", nullable = false)
    private Product product;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="attribute_value_id", nullable = false)
    private AttributeValue attributeValue;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="attribute_id", nullable = false)
    private Attribute attribute;
}
