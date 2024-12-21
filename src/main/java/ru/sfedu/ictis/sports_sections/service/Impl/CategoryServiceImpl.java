package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.CategoryDtoRequest;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.repository.CategoryRepository;
import ru.sfedu.ictis.sports_sections.service.CategoryService;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;

    public CategoryServiceImpl(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    @Override
    public List<CategoryEntity> getAllCategories() {
        return categoryRepository.findAll();
    }

    @Override
    public CategoryEntity addCategory(CategoryDtoRequest categoryDtoRequest) {
        if (categoryRepository.existsByName(categoryDtoRequest.getName())) {
            throw new CustomException(ErrorCodes.CATEGORY_ALREADY_EXISTS);
        }

        CategoryEntity categoryEntity = new CategoryEntity(categoryDtoRequest.getName());
        categoryRepository.save(categoryEntity);
        return categoryEntity;
    }

    @Override
    public CategoryEntity getCategoryById(Long id) {
        return categoryRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.CATEGORY_NOT_FOUND));
    }

    @Override
    public CategoryEntity replaceCategoryById(Long id, CategoryDtoRequest categoryDtoRequest) {
        CategoryEntity categoryEntity = categoryRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.CATEGORY_NOT_FOUND));
        categoryEntity.setName(categoryDtoRequest.getName());
        categoryRepository.save(categoryEntity);
        return categoryEntity;
    }

    @Override
    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }
}
