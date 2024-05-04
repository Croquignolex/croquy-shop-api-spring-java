package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@MappedSuperclass
public abstract class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    protected String id;

    @Column(name = "is_enabled", nullable = false)
    protected Boolean enabled = true;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    protected String description = "";

    @ManyToOne
    @JoinColumn(name = "creator_id")
    protected User creator;

    @Column(name = "created_at")
    protected Date createdAt = new Date();

    @Column(name = "updated_at")
    protected Date updatedAt = new Date();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}

