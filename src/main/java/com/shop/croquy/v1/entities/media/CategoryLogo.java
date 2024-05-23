package com.shop.croquy.v1.entities.media;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.shop.croquy.v1.entities.Category;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "cs_category_logos")
public class CategoryLogo extends MediaBaseEntity {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "category_id")
    private Category category;
}