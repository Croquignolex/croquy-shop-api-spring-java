package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cs_categories")
public class Category extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @ManyToOne
    @JoinColumn(name = "group_id")
    private Group group;

//    @OneToMany(mappedBy = "category")
//    private Set<Product> products = new HashSet<>();

    @JsonIgnore
    public boolean isNonDeletable() {
//        return (long) products.size() > 0 ;
        return false;
    }
}