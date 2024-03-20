package com.shop.croquy.v1.models;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Entity
@NoArgsConstructor
@Table(name = "shops")
public class Shop {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "slug", nullable = false, unique = true)
    private String slug;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

//    @ManyToOne
//    @JoinColumn(name = "creator_id")
//    private User creator;
//
//    @OneToMany(mappedBy = "shop")
//    private Set<InventoryHistory> inventoryHistories = new HashSet<>();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}



