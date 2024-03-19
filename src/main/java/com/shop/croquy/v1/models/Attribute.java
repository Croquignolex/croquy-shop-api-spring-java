package com.shop.croquy.v1.models;

import com.shop.croquy.v1.enums.AttributeType;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
//@Entity
@NoArgsConstructor
//@Table(name = "attributes")
public class Attribute {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttributeType type = AttributeType.TEXT;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "description")
    private String description;

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


