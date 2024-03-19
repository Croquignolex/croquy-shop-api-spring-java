package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.models.Shop;
import com.shop.croquy.v1.repositories.ShopPagingAndSortingRepository;
import com.shop.croquy.v1.services.interfaces.IShopsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopsService implements IShopsService {
    private final ShopPagingAndSortingRepository shopPagingAndSortingRepository;

    @Override
    public Page<Shop> getPaginatedShops() {
        return shopPagingAndSortingRepository.findAll();
    }
}