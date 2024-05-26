package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.shop.croquy.v1.enums.AttributeType;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cs_attributes")
public class Attribute extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private AttributeType type = AttributeType.TEXT;

    @JsonIgnore
    public boolean isNonDeletable() {
        return false;
    }
}


