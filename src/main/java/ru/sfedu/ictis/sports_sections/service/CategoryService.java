package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.CategoryDtoRequest;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;

import java.util.List;

public interface CategoryService {
    List<CategoryEntity> getAllCategories();

    CategoryEntity addCategory(CategoryDtoRequest categoryDtoRequest);

    CategoryEntity getCategoryById(Long id);

    CategoryEntity replaceCategoryById(Long id, CategoryDtoRequest categoryDtoRequest);

    void deleteCategoryById(Long id);
}
