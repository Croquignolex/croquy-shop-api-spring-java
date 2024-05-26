package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cs_attribute_values")
public class AttributeValue extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "value", nullable = false)
    private String value;

    @JsonIgnore
    public boolean isNonDeletable() {
        return false;
    }
}



