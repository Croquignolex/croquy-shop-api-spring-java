package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.*;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

@Repository
public interface CategoryPagingAndSortingRepository extends PagingAndSortingRepository<Category, String> {
    Page<Category> findAllByNameContainsOrGroupIsInOrCreatorIsIn(String nameNeedle, Collection<Group> groups, Collection<User> creator, Pageable pageable);
    Page<Category> findAllByNameContainsOrCreatorIsInAndGroupId(String nameNeedle, Collection<User> creator, String groupId, Pageable pageable);
    Page<Category> findAllByGroupId(String groupId, Pageable pageable);
}
