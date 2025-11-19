package com.my.pet.project.where_to_go_with_friends.category.service;

import com.my.pet.project.where_to_go_with_friends.category.dao.CategoryRepository;
import com.my.pet.project.where_to_go_with_friends.category.dto.CategoryDto;
import com.my.pet.project.where_to_go_with_friends.category.dto.NewCategoryDto;
import com.my.pet.project.where_to_go_with_friends.category.mapper.CategoryMapper;
import com.my.pet.project.where_to_go_with_friends.category.model.Category;
import com.my.pet.project.where_to_go_with_friends.event.service.EventService;
import com.my.pet.project.where_to_go_with_friends.exceptions.ConflictException;
import com.my.pet.project.where_to_go_with_friends.exceptions.NotFoundException;
import com.my.pet.project.where_to_go_with_friends.exceptions.ValidationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final EventService eventService;

    @Override
    @Transactional
    public CategoryDto create(NewCategoryDto categoryDto) {
        log.info("Добавление новой категории.");
        Category category = CategoryMapper.mapToCategory(categoryDto);
        checkConditions(category);
        checkName(category);
        category = categoryRepository.save(category);

        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    @Transactional
    public CategoryDto updateCategory(NewCategoryDto categoryDto, Long catId) {
        log.info("Изменение категории.");
        checkId(catId);
        Category newCategory = CategoryMapper.mapToCategory(categoryDto);

        Category oldCategory = findCategoryById(catId);

        if ((newCategory.getName() != null) && (!oldCategory.getName().equals(newCategory.getName()))) {
            checkName(newCategory);
        }

        oldCategory.setName(newCategory.getName());

        return CategoryMapper.mapToCategoryDto(oldCategory);
    }

    @Override
    public List<CategoryDto> getCategories(Integer from, Integer size) {
        log.info("Получение информации о категориях.");
        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        Page<Category> categoryPage;
        categoryPage = categoryRepository.findAll(pageable);

        return categoryPage.getContent()
                .stream()
                .map(CategoryMapper::mapToCategoryDto)
                .toList();
    }

    @Override
    public CategoryDto getCategoryById(Long catId) {
        log.info("Получение информации о категории по её идентификатору.");
        checkId(catId);
        Category category = findCategoryById(catId);
        return CategoryMapper.mapToCategoryDto(category);
    }

    @Override
    @Transactional
    public void deleteCategoryById(Long catId) {
        log.info("Удаление категории.");

        checkId(catId);

        categoryRepository.findById(catId).orElseThrow(
                () -> new NotFoundException("Категория с Id " + catId + " не найдена"));

        if (!eventService.findEventsByCategoryId(catId).isEmpty()) {
            throw new ConflictException("Существуют события, связанные с категорией");
        }

        categoryRepository.deleteById(catId);
    }

    private void checkId(Long id) {
        if (id == null) {
            log.warn("Id должен быть указан.");
            throw new ValidationException("Id должен быть указан");
        }
    }

    private void checkName(Category category) {
        if (categoryRepository.existsByName(category.getName())) {
            log.warn("Категория уже существует.");
            throw new ConflictException("Категория с name = " + category.getName() + " уже существует");
        }
    }

    private void checkConditions(Category category) {
        if (category.getName().isEmpty()) {
            log.warn("Задано пустое имя пользователя.");
            throw new ValidationException("Задано пустое имя пользователя");
        }
    }

    private Category findCategoryById(Long catId) {
        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с Id " + catId + " не найдена"));
    }
}
