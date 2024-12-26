package ru.sfedu.ictis.sports_sections.controller;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Positive;
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
import ru.sfedu.ictis.sports_sections.dto.request.EnrollDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.EnrollStatusDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.CreateResponse;
import ru.sfedu.ictis.sports_sections.dto.response.GetEnrollmentsDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.PagenableResponse;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;
import ru.sfedu.ictis.sports_sections.repository.EnrollmentRepository;
import ru.sfedu.ictis.sports_sections.service.EnrollmentService;

@RestController
@RequestMapping("/enrollment")
@Validated
public class EnrollmentController {
    private final EnrollmentService enrollmentService;

    public EnrollmentController(EnrollmentService enrollmentService,
                                EnrollmentRepository enrollmentRepository) {
        this.enrollmentService = enrollmentService;
    }

    @PostMapping
    public ResponseEntity<CreateResponse> createEnrollment(
            @Valid
            @RequestBody
            EnrollDtoRequest enrollDtoRequest) {
        return ResponseEntity.ok(new CreateResponse(enrollmentService.createEnroll(enrollDtoRequest)));
    }

    @GetMapping
    public ResponseEntity<CustomSuccessResponse<PagenableResponse<GetEnrollmentsDtoResponse>>> getEnrollments(
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Min(value = 1, message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            Integer page,
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Max(value = 100, message = ValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
            @Min(value = 1, message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1)
            Integer perPage) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(enrollmentService.getEnrollments(page, perPage)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<GetEnrollmentsDtoResponse>> getEnrollmentById(
            @PathVariable
            @Positive
            Long id) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(enrollmentService.getEnrollment(id)));
    }

    @GetMapping("/user")
    public ResponseEntity<CustomSuccessResponse<PagenableResponse<GetEnrollmentsDtoResponse>>> getEnrollmentsForUser(
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Min(value = 1, message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            Integer page,
            @RequestParam
            @Positive(message = ValidationConstants.TASKS_PAGE_GREATER_OR_EQUAL_1)
            @Max(value = 100, message = ValidationConstants.TASKS_PER_PAGE_LESS_OR_EQUAL_100)
            @Min(value = 1, message = ValidationConstants.TASKS_PER_PAGE_GREATER_OR_EQUAL_1)
            Integer perPage) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(enrollmentService.getEnrollmentsForUser(page, perPage)));
    }

    @GetMapping("/section/{id}")
    public ResponseEntity<CustomSuccessResponse<PagenableResponse<GetEnrollmentsDtoResponse>>> getEnrollmentsForSection(
            @PathVariable
            @Positive
            Long id,
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
                new CustomSuccessResponse<>(enrollmentService.getEnrollmentsForSection(id, page, perPage))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> putStatusEnrollment(
            @PathVariable
            @Positive
            Long id,
            @Valid
            @RequestBody
            EnrollStatusDtoRequest enrollDtoRequest) {
        enrollmentService.putStatusEnroll(id, enrollDtoRequest);
        return ResponseEntity.ok(new CustomSuccessResponse());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> deleteEnrollment(
            @PathVariable
            @Positive
            Long id) {
        enrollmentService.deleteEnroll(id);
        return ResponseEntity.ok(new CustomSuccessResponse());
    }
}
