package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.AddressMorphType;
import com.shop.croquy.v1.enums.AddressType;
import com.shop.croquy.v1.enums.TaggableMorphType;
import com.shop.croquy.v1.models.Address;
import com.shop.croquy.v1.models.Tag;
import com.shop.croquy.v1.models.Taggable;
import com.shop.croquy.v1.repositories.AddressRepository;
import com.shop.croquy.v1.repositories.TaggableRepository;
import com.shop.croquy.v1.services.interfaces.IShopService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopService implements IShopService {
    private final AddressRepository addressRepository;
    private final TaggableRepository taggableRepository;

    @Override
    public Address getAddressById(String id) {
        return addressRepository
                .findByAddressMorphIdAndAddressMorphTypeAndType(id, AddressMorphType.SHOP, AddressType.DEFAULT)
                .orElse(null);
    }

//    @Override
//    public List<Tag> getTagsById(String id) {
//        List<Taggable> taggable = taggableRepository.findByTaggableMorphIdAndTaggableMorphType(id, TaggableMorphType.SHOP);
//
//        return taggable.stream().map(Taggable::getTag).toList();
//    }
}