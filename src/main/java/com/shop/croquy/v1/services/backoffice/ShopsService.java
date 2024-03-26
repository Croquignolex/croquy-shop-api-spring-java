package com.shop.croquy.v1.services.backoffice;

import com.shop.croquy.v1.dao.backoffice.GenericResponse;
import com.shop.croquy.v1.dao.backoffice.shop.ShopStoreRequest;
import com.shop.croquy.v1.models.Shop;
import com.shop.croquy.v1.models.User;
import com.shop.croquy.v1.repositories.ShopPagingAndSortingRepository;
import com.shop.croquy.v1.repositories.ShopRepository;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.interfaces.IShopsService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

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
    public GenericResponse create(ShopStoreRequest request, String creatorUsername) {
        Optional<Shop> shop = shopRepository.findFistByNameOrSlug(request.getName(), request.getSlug());

        if(shop.isEmpty()) {
            var creator = userRepository.findByUsername(creatorUsername).orElse(null);
            shopRepository.save(request.toShop(creator));

            return GenericResponse.builder().code(HttpStatus.CREATED).build();
        }

        return GenericResponse.builder().code(HttpStatus.BAD_REQUEST).message("UNIQUE_SLUG_ERROR").build();
    }

    @Override
    public GenericResponse deleteById(String id) {
        Optional<Shop> shop = shopRepository.findById(id);

        if(shop.isPresent()) {
            shopRepository.deleteById(id);

            return GenericResponse.builder().code(HttpStatus.NO_CONTENT).build();
        }

        return GenericResponse.builder().code(HttpStatus.BAD_REQUEST).message("NOT_FOUND_ERROR").build();
    }
}