package com.shop.croquy.v1.entities.address;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.shop.croquy.v1.entities.Shop;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cs_shop_addresses")
public class ShopAddress extends AddressBaseEntity {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "shop_id")
    private Shop shop;
}

