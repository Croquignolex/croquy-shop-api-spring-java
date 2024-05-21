package com.shop.croquy.v1.entities.media;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.shop.croquy.v1.entities.User;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class MediaBaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    protected String id;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "original_name", nullable = false)
    protected String originalName;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Column(name = "size")
    protected long size;

    @Column(name = "path", nullable = false, unique = true)
    protected String path;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @ManyToOne
    @JoinColumn(name = "creator_id")
    protected User creator;

    @Column(name = "created_at")
    protected Date createdAt = new Date();
}

