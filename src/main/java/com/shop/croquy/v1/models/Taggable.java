package com.shop.croquy.v1.models;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @Enumerated(EnumType.STRING)
    @Column(name = "taggable_morph_type", nullable = false)
    private TaggableMorphType taggableMorphType;

    @Column(name = "taggable_morph_id", nullable = false)
    private String taggableMorphId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "tag_id", nullable = false)
    private Tag tag;
}




