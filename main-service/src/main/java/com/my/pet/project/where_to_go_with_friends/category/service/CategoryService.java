package com.my.pet.project.where_to_go_with_friends.category.service;



import com.my.pet.project.where_to_go_with_friends.category.dto.CategoryDto;
import com.my.pet.project.where_to_go_with_friends.category.dto.NewCategoryDto;

import java.util.List;

public interface CategoryService {
    CategoryDto create(NewCategoryDto categoryDto);

    void deleteCategoryById(Long catId);

    CategoryDto updateCategory(NewCategoryDto categoryDto, Long catId);

    List<CategoryDto> getCategories(Integer from, Integer size);

    CategoryDto getCategoryById(Long catId);
}
