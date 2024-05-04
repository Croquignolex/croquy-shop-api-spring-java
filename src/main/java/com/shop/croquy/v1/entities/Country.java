package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

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

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private Set<State> states = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "country")
    private Set<Inventory> inventories = new HashSet<>();

    @JsonIgnore
//    public boolean isNonDeletable() {
//        return (long) states.size() > 0 || (long) inventories.size() > 0;
//    }
    public boolean isNonDeletable() {
        return (long) states.size() > 0 ;
    }
}

