package com.my.pet.project.where_to_go_with_friends.category.mapper;

import com.my.pet.project.where_to_go_with_friends.category.dto.CategoryDto;
import com.my.pet.project.where_to_go_with_friends.category.dto.NewCategoryDto;
import com.my.pet.project.where_to_go_with_friends.category.model.Category;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;


@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CategoryMapper {

    public static CategoryDto mapToCategoryDto(Category category) {
        return CategoryDto.builder()
                .id(category.getId())
                .name(category.getName())
                .build();
    }

    public static Category mapToCategory(CategoryDto categoryDto) {
        Category category = new Category();
        category.setId(categoryDto.getId());
        category.setName(categoryDto.getName());
        return category;
    }

    public static Category mapToCategory(NewCategoryDto categoryDto) {
        Category category = new Category();
        category.setName(categoryDto.getName());
        return category;
    }
}
