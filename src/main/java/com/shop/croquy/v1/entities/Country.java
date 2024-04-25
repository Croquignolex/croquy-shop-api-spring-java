package com.shop.croquy.v1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    private List<State> states = new ArrayList<>();

//    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
//    @OneToMany(mappedBy = "country", fetch = FetchType.EAGER)
//    private Set<Inventory> inventories = new HashSet<>();
}

