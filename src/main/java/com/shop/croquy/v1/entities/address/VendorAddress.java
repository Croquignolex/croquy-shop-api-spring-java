package com.shop.croquy.v1.entities.address;

import com.fasterxml.jackson.annotation.JsonProperty;

import com.shop.croquy.v1.entities.Vendor;
import jakarta.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "cs_vendor_addresses")
public class VendorAddress extends AddressBaseEntity {
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToOne
    @JoinColumn(name = "vendor_id")
    private Vendor vendor;
}

