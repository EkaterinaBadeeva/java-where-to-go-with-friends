package com.my.pet.project.where_to_go_with_friends.category.dao;

import com.my.pet.project.where_to_go_with_friends.category.model.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepository extends JpaRepository<Category, Long> {
    Boolean existsByName(String name);
}
