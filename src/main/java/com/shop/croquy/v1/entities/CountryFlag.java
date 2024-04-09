package com.shop.croquy.v1.entities;

import com.shop.croquy.v1.enums.MediaType;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "country_flags")
public class CountryFlag extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "type", nullable = false)
    @Enumerated(EnumType.STRING)
    private MediaType type;

    @Column(name = "path", nullable = false)
    private String path;

    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;
}