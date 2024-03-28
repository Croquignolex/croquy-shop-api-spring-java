package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.*;
import com.shop.croquy.v1.entities.Address;
import com.shop.croquy.v1.entities.Media;
import com.shop.croquy.v1.repositories.AddressRepository;
import com.shop.croquy.v1.repositories.TaggableRepository;
import com.shop.croquy.v1.repositories.MediaRepository;
import com.shop.croquy.v1.services.interfaces.IVendorService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class VendorService implements IVendorService {
    private final MediaRepository mediaRepository;
    private final AddressRepository addressRepository;
    private final TaggableRepository taggableRepository;

    @Override
    public Media getLogoById(String id) {
         return mediaRepository
                 .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.VENDOR, MediaType.LOGO)
                 .orElse(null);
    }

    @Override
    public Address getAddressById(String id) {
        return addressRepository
                .findByAddressMorphIdAndAddressMorphTypeAndType(id, AddressMorphType.VENDOR, AddressType.DEFAULT)
                .orElse(null);
    }

//    @Override
//    public List<Tag> getTagsById(String id) {
//        List<Taggable> taggable = taggableRepository.findByTaggableMorphIdAndTaggableMorphType(id, TaggableMorphType.VENDOR);
//
//        return taggable.stream().map(Taggable::getTag).toList();
//    }
}