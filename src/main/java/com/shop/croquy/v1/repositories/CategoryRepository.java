package com.shop.croquy.v1.repositories;

import com.shop.croquy.v1.entities.Category;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, String> {
//    List<Category> findAllByGroupId(String groupId);
}
