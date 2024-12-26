package ru.sfedu.ictis.sports_sections.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import ru.sfedu.ictis.sports_sections.dto.request.SectionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.CreateSectionResponse;
import ru.sfedu.ictis.sports_sections.dto.response.GetSectionDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.PagenableResponse;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;
import ru.sfedu.ictis.sports_sections.service.SectionService;

import java.util.Set;

@Getter
@RestController
@RequestMapping("/section")
@Validated
public class SectionController {
    private final SectionService sectionService;

    public SectionController(SectionService sectionService) {
        this.sectionService = sectionService;
    }

    @PostMapping
    public ResponseEntity<CreateSectionResponse> createSection(
            @Valid
            @RequestBody
            SectionDtoRequest sectionDtoRequest) {
        return ResponseEntity.ok(new CreateSectionResponse(sectionService.createSection(sectionDtoRequest)));
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<PagenableResponse>> getSections(
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Min(value = 1, message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            Integer page,
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Max(value = 100, message = ValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
            @Min(value = 1, message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1)
            Integer perPage) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(sectionService.getSections(page, perPage)));
    }

    @GetMapping("/find")
    public ResponseEntity<CustomSuccessResponse<PagenableResponse>> findSections(
            @RequestParam(required = false)
            String trainer,
            @RequestParam(required = false)
            String keywords,
            @RequestParam(required = false)
            String location,
            @RequestParam(required = false)
            Set<String> categories,
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Min(value = 1, message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            Integer page,
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Max(value = 100, message = ValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
            @Min(value = 1, message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1)
            Integer perPage) {
        return ResponseEntity.ok(
                    new CustomSuccessResponse<>(
                            sectionService.findSections(trainer, keywords, location, categories, page, perPage)
                    )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<GetSectionDtoResponse>> getSectionById(
            @PathVariable
            @Positive
            Long id) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(sectionService.getSectionById(id)));
    }

    @GetMapping("/trainer/{id}")
    public ResponseEntity<CustomSuccessResponse<PagenableResponse>> getAllSectionsOfTrainer(
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Min(value = 1, message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            Integer page,
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Max(value = 100, message = ValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
            @Min(value = 1, message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1)
            Integer perPage,
            @PathVariable
            @Positive
            Long id) {
        return ResponseEntity.ok(
                new CustomSuccessResponse<>(sectionService.getAllSectionsOfTrainer(page, perPage, id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> putSection(
            @PathVariable
            @Positive
            Long id,
            @Valid
            @RequestBody
            SectionDtoRequest sectionDtoRequest) {
        sectionService.putSection(id, sectionDtoRequest);
        return ResponseEntity.ok(new CustomSuccessResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> deleteSection(
            @PathVariable
            @Positive
            Long id) {
        sectionService.deleteSection(id);
        return ResponseEntity.ok(new CustomSuccessResponse());
    }
}
