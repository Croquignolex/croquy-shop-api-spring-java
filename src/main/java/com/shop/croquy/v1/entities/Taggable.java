package com.shop.croquy.v1.entities;

import com.shop.croquy.v1.enums.TaggableMorphType;

import jakarta.persistence.*;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@NoArgsConstructor
@Table(name = "taggables")
public class Taggable {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id", nullable = false)
    private String id;

    @Enumerated(EnumType.STRING)
    @Column(name = "taggable_morph_type", nullable = false)
    private TaggableMorphType taggableMorphType;

    @Column(name = "taggable_morph_id", nullable = false)
    private String taggableMorphId;

//    @ManyToOne
//    @JoinColumn(name = "tag_id")
//    private Tag tag;
}




