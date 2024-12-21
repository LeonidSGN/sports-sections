package ru.sfedu.ictis.sports_sections.controller;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import ru.sfedu.ictis.sports_sections.dto.request.CategoryDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;
import ru.sfedu.ictis.sports_sections.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/category")
@Validated
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<List<CategoryEntity>>> getAllCategories() {
        return ResponseEntity.ok(new CustomSuccessResponse<>(categoryService.getAllCategories()));
    }

    @PostMapping
    public ResponseEntity<CustomSuccessResponse<CategoryEntity>> addCategory (
            @Valid
            @RequestBody
            CategoryDtoRequest categoryDtoRequest) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(categoryService.addCategory(categoryDtoRequest)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<CategoryEntity>> getCategoryById(@PathVariable
                                                                                 Long id) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(categoryService.getCategoryById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<CategoryEntity>> replaceCategoryById(@PathVariable
                                                                                     Long id,
                                                                                     @Valid
                                                                                     @RequestBody
                                                                                     CategoryDtoRequest categoryDtoRequest) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(categoryService.replaceCategoryById(id, categoryDtoRequest)));
    }

    @DeleteMapping("/{id}")
        public ResponseEntity<CustomSuccessResponse> deleteCategoryById(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return ResponseEntity.ok(new CustomSuccessResponse());
    }
}
