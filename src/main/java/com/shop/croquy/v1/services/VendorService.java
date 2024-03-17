package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.AddressMorphType;
import com.shop.croquy.v1.enums.MediaMorphType;
import com.shop.croquy.v1.models.Address;
import com.shop.croquy.v1.models.Media;
import com.shop.croquy.v1.repositories.AddressRepository;
import com.shop.croquy.v1.repositories.MediaRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VendorService implements IVendorService {
    private final MediaRepository mediaRepository;
    private final AddressRepository addressRepository;

    @Override
    public Media getLogo(String id) {
         return mediaRepository.findByMediaMorphIdAndMediaMorphType(id, MediaMorphType.VENDOR).orElse(null);
    }

    @Override
    public Address getAddress(String id) {
        return addressRepository.findByAddressMorphIdAndAddressMorphType(id, AddressMorphType.VENDOR).orElse(null);
    }
}