package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.enums.Role;
import com.shop.croquy.v1.models.User;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface UserPagingAndSortingRepository extends PagingAndSortingRepository<User, Integer> {
    Page<User> findAllByIdIsNotAndRoleIn(Integer id, Collection<Role> roles, Pageable pageable);
}
