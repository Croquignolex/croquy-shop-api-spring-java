package com.shop.croquy.v1.models;

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
@Table(name = "groups")
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "group", cascade = CascadeType.DETACH)
    private Set<Category> categories = new HashSet<>();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}


