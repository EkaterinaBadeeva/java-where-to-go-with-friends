package com.my.pet.project.where_to_go_with_friends.category.controller;

import com.my.pet.project.where_to_go_with_friends.category.dto.CategoryDto;
import com.my.pet.project.where_to_go_with_friends.category.dto.NewCategoryDto;
import com.my.pet.project.where_to_go_with_friends.category.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/admin/categories")
@RequiredArgsConstructor
public class AdminCategoryController {
    private final CategoryService categoryService;

    //POST /admin/categories
    // добавить новую категорию
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CategoryDto create(@Valid @RequestBody NewCategoryDto categoryDto) {
        return categoryService.create(categoryDto);
    }

    //PATCH /admin/categories/{catId}
    // измененить категорию
    @PatchMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto updateCategory(@Valid @RequestBody NewCategoryDto categoryDto,
                                      @PathVariable Long catId) {
        return categoryService.updateCategory(categoryDto, catId);
    }

    //DELETE /admin/categories/{catId}
    // удалить категорию по id
    @DeleteMapping("/{catId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteCategory(@PathVariable Long catId) {
        categoryService.deleteCategoryById(catId);
    }
}
