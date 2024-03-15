package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.ViewMorphType;

import com.fasterxml.jackson.annotation.JsonProperty;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "views", uniqueConstraints = @UniqueConstraint(columnNames = {"viewer_id", "vieww_morph_id", "vieww_morph_type"}))
public class View {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Enumerated(EnumType.STRING)
    @Column(name = "view_morph_type", nullable = false)
    private ViewMorphType viewMorphType;

    @Column(name = "view_morph_id")
    private String viewMorphId;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name="viewer_id")
    private User viewer;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "deleted_at")
    private Date deleted;

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}