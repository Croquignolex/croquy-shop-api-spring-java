package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "countries")
public class Country extends BaseEntity {
    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "phone_code")
    private String phoneCode;

    @OneToOne(mappedBy = "country")
    private CountryFlag flag;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "country", fetch = FetchType.LAZY)
    private Set<State> states = new HashSet<>();

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "country")
    private Set<Inventory> inventories = new HashSet<>();
}

