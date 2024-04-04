package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dto.backoffice.shop.ShopStoreRequest;
import com.shop.croquy.v1.dto.backoffice.shop.ShopUpdateRequest;
import com.shop.croquy.v1.entities.Shop;
import com.shop.croquy.v1.entities.User;
import com.shop.croquy.v1.helpers.ErrorMessagesHelper;
import com.shop.croquy.v1.repositories.ShopPagingAndSortingRepository;
import com.shop.croquy.v1.repositories.ShopRepository;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.interfaces.IShopsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class ShopsService implements IShopsService {
    private final ShopPagingAndSortingRepository shopPagingAndSortingRepository;
    private final ShopRepository shopRepository;
    private final UserRepository userRepository;

    @Override
    public Page<Shop> getPaginatedShops(int pageNumber, int pageSize, String needle) {
        Pageable pageable = PageRequest.of(
                pageNumber,
                pageSize,
                Sort.by(Sort.Direction.DESC, "createdAt")
        );

        if(StringUtils.isNotEmpty(needle)) {
            List<User> users = userRepository.findByUsernameContains(needle);

            return shopPagingAndSortingRepository.findAllByNameContainsOrCreatorIsIn(needle, users, pageable);
        }

        return shopPagingAndSortingRepository.findAll(pageable);
    }

    @Override
    public Shop getShopById(String id) {
        return shopRepository.findById(id).orElseThrow(() -> new DataIntegrityViolationException(ErrorMessagesHelper.SHOP_ID_NOT_FOUND + id));
    }

    @Override
    public void storeShopWithCreator(ShopStoreRequest request, String creatorUsername) {
        if(shopRepository.findFistByName(request.getName()).isPresent()) {
            throw new DataIntegrityViolationException(ErrorMessagesHelper.SHOP_NAME_ALREADY_EXIST + request.getName());
        }

        if(shopRepository.findFistBySlug(request.getSlug()).isPresent()) {
            throw new DataIntegrityViolationException(ErrorMessagesHelper.SHOP_SLUG_ALREADY_EXIST + request.getSlug());
        }

        var creator = userRepository.findByUsername(creatorUsername).orElse(null);
        shopRepository.save(request.toShop(creator));
    }

    @Override
    public void updateShopById(ShopUpdateRequest request, String id) {
        if(shopRepository.findFistByNameAndIdNot(request.getName(), id).isPresent()) {
            throw new DataIntegrityViolationException(ErrorMessagesHelper.SHOP_NAME_ALREADY_EXIST + request.getName());
        }

        if(shopRepository.findFistBySlugAndIdNot(request.getSlug() , id).isPresent()) {
            throw new DataIntegrityViolationException(ErrorMessagesHelper.SHOP_SLUG_ALREADY_EXIST + request.getSlug());
        }

        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ErrorMessagesHelper.SHOP_ID_NOT_FOUND + id));

        shop.setName(request.getName());
        shop.setSlug(request.getSlug());
        shop.setDescription(request.getDescription());

        shopRepository.save(shop);
    }

    @Override
    public void destroyById(String id) {
        if(shopRepository.findById(id).isEmpty()) {
            throw new DataIntegrityViolationException(ErrorMessagesHelper.SHOP_ID_NOT_FOUND + id);
        }

        shopRepository.deleteById(id);
    }

    @Override
    public void toggleStatusById(String id) {
        Shop shop = shopRepository.findById(id)
                .orElseThrow(() -> new DataIntegrityViolationException(ErrorMessagesHelper.SHOP_ID_NOT_FOUND + id));

        shop.setEnabled(!shop.getEnabled());

        shopRepository.save(shop);
    }
}