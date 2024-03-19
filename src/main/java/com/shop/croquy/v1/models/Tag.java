package com.shop.croquy.v1.models;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
//@Entity
@NoArgsConstructor
//@Table(name = "tags")
public class Tag {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private User creator;

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}




