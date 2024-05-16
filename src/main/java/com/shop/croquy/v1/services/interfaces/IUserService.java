package com.shop.croquy.v1.services.interfaces;

import org.springframework.security.core.userdetails.UserDetailsService;

public interface IUserService {
    UserDetailsService userDetailsService();
//    Media getAvatarById(String id);
//    Address getDefaultAddressById(String id);
//    Address getBillingAddressById(String id);
//    Address getShippingAddressById(String id);
    Boolean isAdminByUsername(String username);
    Boolean isSuperAdminByUsername(String username);
    Boolean isCustomerByUsername(String username);
    Boolean isManagerByUsername(String username);
    Boolean isSellerByUsername(String username);
}
