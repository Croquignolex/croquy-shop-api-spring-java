package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.entities.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<User, String> {
    Page<User> findAllByIdIsNotAndRoleIn(String id, Collection<Role> roles, Pageable pageable);
}
