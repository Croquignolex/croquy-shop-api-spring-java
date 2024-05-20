package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
//@Entity
@NoArgsConstructor
//@Table(name = "ratings")
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "note", nullable = false)
    private Integer note = 0;

    @Column(name = "comment")
    private String comment;

//    @Enumerated(EnumType.STRING)
//    @Column(name = "rating_morph_type", nullable = false)
//    private RatingMorphType ratingMorphType;

    @Column(name = "rating_morph_id", nullable = false)
    private String ratingMorphId;

    @ManyToOne
    @JoinColumn(name = "rater_id")
    private User rater;

    @Column(name = "is_enabled", nullable = false)
    private Boolean enabled = true;

    @Column(name = "created_at")
    private Date createdAt = new Date();

    @Column(name = "updated_at")
    private Date updatedAt = new Date();

    @PreUpdate
    public void updateTrigger() {
        this.updatedAt = new Date();
    }
}
