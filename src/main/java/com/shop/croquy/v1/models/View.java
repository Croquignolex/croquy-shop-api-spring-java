package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.ViewMorphType;

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
    @Column(name = "id", nullable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "view_morph_type", nullable = false)
    private ViewMorphType viewMorphType;

    @Column(name = "view_morph_id", nullable = false)
    private String viewMorphId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "viewer_id", nullable = false)
    private User viewer;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}