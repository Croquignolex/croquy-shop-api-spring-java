package com.shop.croquy.v1.entities.media;

import com.fasterxml.jackson.annotation.JsonIgnore;

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

    @JsonIgnore
    @Column(name = "original_name", nullable = false)
    protected String originalName;

    @JsonIgnore
    @Column(name = "size")
    protected long size;

    @Column(name = "path", nullable = false, unique = true)
    protected String path;

    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "creator_id")
    protected User creator;

    @Column(name = "created_at")
    protected Date createdAt = new Date();
}

