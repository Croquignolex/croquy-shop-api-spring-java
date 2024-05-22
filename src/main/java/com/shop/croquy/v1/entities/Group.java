package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.shop.croquy.v1.entities.media.GroupBanner;
import com.shop.croquy.v1.entities.media.GroupLogo;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cs_groups")
public class Group extends BaseEntity {
    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @OneToOne(mappedBy = "group")
    private GroupLogo logo;

    @OneToOne(mappedBy = "group")
    private GroupBanner banner;

    @JsonIgnore
    @OneToMany(mappedBy = "group")
    private Set<Category> categories = new HashSet<>();

    @JsonIgnore
    public boolean isNonDeletable() {
        return (long) categories.size() > 0 ;
    }
}



