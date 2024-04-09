package com.shop.croquy.v1.services;

import com.shop.croquy.v1.enums.*;
import com.shop.croquy.v1.entities.Address;
import com.shop.croquy.v1.entities.Media;
import com.shop.croquy.v1.repositories.AddressRepository;
import com.shop.croquy.v1.repositories.MediaRepository;
import com.shop.croquy.v1.repositories.UserRepository;
import com.shop.croquy.v1.services.interfaces.IUserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService implements IUserService {
    private final UserRepository userRepository;
    private final MediaRepository mediaRepository;
    private final AddressRepository addressRepository;

    @Override
    public UserDetailsService userDetailsService() {
        return username -> userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
    }

    @Override
    public Media getAvatarById(String id) {
        /*return mediaRepository
                .findByMediaMorphIdAndMediaMorphTypeAndType(id, MediaMorphType.USER, MediaType.AVATAR)
                .orElse(null);*/
        return null;
    }

    @Override
    public Address getDefaultAddressById(String id) {
        return addressRepository
                .findByAddressMorphIdAndAddressMorphTypeAndType(id, AddressMorphType.USER, AddressType.DEFAULT)
                .orElse(null);
    }

    @Override
    public Address getBillingAddressById(String id) {
        return addressRepository
                .findByAddressMorphIdAndAddressMorphTypeAndType(id, AddressMorphType.USER, AddressType.BILLING)
                .orElse(null);
    }

    @Override
    public Address getShippingAddressById(String id) {
        return addressRepository
                .findByAddressMorphIdAndAddressMorphTypeAndType(id, AddressMorphType.USER, AddressType.SHIPPING)
                .orElse(null);
    }

    @Override
    public Boolean isAdminByUsername(String username) {
        return  isRoleByUsername(username, List.of(Role.ADMIN));
    }

    @Override
    public Boolean isSuperAdminByUsername(String username) {
        return  isRoleByUsername(username, List.of(Role.SUPER_ADMIN));
    }

    @Override
    public Boolean isCustomerByUsername(String username) {
        return  isRoleByUsername(username, List.of(Role.CUSTOMER));
    }

    @Override
    public Boolean isManagerByUsername(String username) {
        return  isRoleByUsername(username, List.of(Role.MANAGER));
    }

    @Override
    public Boolean isSellerByUsername(String username) {
        return  isRoleByUsername(username, List.of(Role.SELLER));
    }

    private Boolean isRoleByUsername(String username, Collection<Role> includedRoles) {
        var user = userRepository.findByUsernameAndRoleIn(username, includedRoles).orElse(null);
        return !(user == null);
    }
}