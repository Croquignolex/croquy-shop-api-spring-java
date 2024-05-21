package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.croquy.v1.entities.media.BrandLogo;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "brands")
public class Brand extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "website")
    private String website;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @OneToOne(mappedBy = "brand")
    private BrandLogo logo;

//    @OneToMany(mappedBy = "brand")
//    private Set<Product> products = new HashSet<>();

    @JsonIgnore
    public boolean isNonDeletable() {
//        return (long) products.size() > 0 ;
        return false;
    }
}



