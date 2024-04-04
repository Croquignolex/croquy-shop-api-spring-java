package com.shop.croquy.v1.entities;

import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.enums.MediaType;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "medias")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaType type;

    @Column(name = "path", nullable = false)
    private String path;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description = "";

    @Enumerated(EnumType.STRING)
    @Column(name = "media_morph_type", nullable = false)
    private MediaMorphType mediaMorphType;

    @Column(name = "media_morph_id", nullable = false)
    private String mediaMorphId;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}
