package com.shop.croquy.v1.entities.media;

import com.fasterxml.jackson.annotation.JsonIgnore;

import com.shop.croquy.v1.entities.Group;

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
@Table(name = "cs_group_logos")
public class GroupLogo extends MediaBaseEntity {
    @JsonIgnore
    @OneToOne
    @JoinColumn(name = "group_id")
    private Group group;
}