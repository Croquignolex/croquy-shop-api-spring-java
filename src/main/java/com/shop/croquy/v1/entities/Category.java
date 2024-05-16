package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
//@Entity
@NoArgsConstructor
//@Table(name = "categories")
//@Table(name = "categories", uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "name"}))
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "seo_title")
    private String seoTitle;

    @Column(name = "seo_description")
    private String seoDescription;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description = "";

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

//    @ManyToOne
//    @JoinColumn(name = "creator_id")
//    private User creator;

//    @ManyToOne
//    @JoinColumn(name = "group_id")
//    private Group group;
//
//    @OneToMany(mappedBy = "category")
//    private Set<Product> products = new HashSet<>();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}