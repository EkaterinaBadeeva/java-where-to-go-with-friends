package com.my.pet.project.where_to_go_with_friends.category.controller;

import com.my.pet.project.where_to_go_with_friends.category.dto.CategoryDto;
import com.my.pet.project.where_to_go_with_friends.category.service.CategoryService;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/categories")
@RequiredArgsConstructor
public class PublicCategoryController {
    private final CategoryService categoryService;

    //GET  /categories?from={from}&size={size}
    // получить категории
    // from - количество элементов, которые нужно пропустить для формирования текущего набора,
    // size - количество категорий в наборе
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<CategoryDto> getCategories(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                           @RequestParam(defaultValue = "10") @Positive Integer size) {
        return categoryService.getCategories(from, size);
    }

    //GET  /categories/{catId}
    // получить категории
    // from - количество элементов, которые нужно пропустить для формирования текущего набора,
    // size - количество категорий в наборе
    @GetMapping("/{catId}")
    @ResponseStatus(HttpStatus.OK)
    public CategoryDto getCategoryById(@PathVariable Long catId) {
        return categoryService.getCategoryById(catId);
    }
}
