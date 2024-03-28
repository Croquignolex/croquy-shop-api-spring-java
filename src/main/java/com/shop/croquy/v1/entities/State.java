package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Data
//@Entity
@NoArgsConstructor
//@Table(name = "states", uniqueConstraints = @UniqueConstraint(columnNames = {"group_id", "name"}))
public class State {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

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

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

    @OneToMany(mappedBy = "state")
    private Set<Address> addresses = new HashSet<>();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}