package com.shop.croquy.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.croquy.v1.enums.MediaType;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "cs_medias")
public class Media {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaType type;

    @Column(name = "path", nullable = false)
    private String path;

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
