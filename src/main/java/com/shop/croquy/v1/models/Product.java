package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.*;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
@NoArgsConstructor
@Table(name = "products")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "sku", unique = true)
    private String sku;

    @Column(name = "barcode", unique = true)
    private String barcode;

    @Column(name = "min_price", nullable = false)
    private Integer minPrice = 0;

    @Column(name = "max_price", nullable = false)
    private Integer maxPrice = 0;

    @Column(name = "weight_value", nullable = false)
    private Double weightValue = 0.0;

    @Column(name = "height_value", nullable = false)
    private Double heightValue = 0.0;

    @Column(name = "width_value", nullable = false)
    private Double widthValue = 0.0;

    @Column(name = "depth_value", nullable = false)
    private Double depthValue = 0.0;

    @Column(name = "volume_value", nullable = false)
    private Double volumeValue = 0.0;

    @Column(name = "weight_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private WeightValue weightUnit = WeightValue.GRAM;

    @Column(name = "height_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private DistanceValue heightUnit = DistanceValue.METER;

    @Column(name = "width_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private DistanceValue widthUnit = DistanceValue.METER;

    @Column(name = "depth_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private DistanceValue depthUnit = DistanceValue.METER;

    @Column(name = "volume_unit", nullable = false)
    @Enumerated(EnumType.STRING)
    private QuantityValue volumeUnit = QuantityValue.LITTER;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "deleted_at")
    private Date deleted;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="creator_id", nullable = false)
    private User creator;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="category_id", nullable = false)
    private Category category;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="brand_id", nullable = false)
    private Brand brand;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy="product")
    private Set<Inventory> inventories = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy="product")
    private Set<InventoryHistory> inventoryHistories = new HashSet<>();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}



