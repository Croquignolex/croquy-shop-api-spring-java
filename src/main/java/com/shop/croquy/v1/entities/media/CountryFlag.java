package com.shop.croquy.v1.entities.media;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.shop.croquy.v1.entities.Country;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "cs_country_flags")
public class CountryFlag extends MediaBaseEntity {
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "country_id")
    private Country country;
}