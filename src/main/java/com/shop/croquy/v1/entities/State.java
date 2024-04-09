package com.shop.croquy.v1.entities;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "states")
public class State extends BaseEntity {
    // Unique into Country
    @Column(name = "name", nullable = false)
    private String name;

    @ManyToOne
    @JoinColumn(name = "country_id")
    private Country country;

//    @OneToMany(mappedBy = "state")
//    private Set<Address> addresses = new HashSet<>();
}