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
import ru.sfedu.ictis.sports_sections.dto.request.PutReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.ReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.ReviewDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import ru.sfedu.ictis.sports_sections.service.ReviewService;

import java.util.List;

@RestController
@RequestMapping("/reviews")
@Validated
public class ReviewsController {
    private final ReviewService reviewService;

    public ReviewsController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<CustomSuccessResponse<ReviewDtoResponse>> addReview(
            @Valid
            @RequestBody
            ReviewDtoRequest request) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(reviewService.addReview(request)));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<ReviewDtoResponse>> getReviewUserBySection(
            @PathVariable
            Long id) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(reviewService.getReviewUserBySection(id)));
    }

    @GetMapping("/section/{id}")
    public ResponseEntity<CustomSuccessResponse<List<ReviewDtoResponse>>> getAllReviewsForSection(
            @PathVariable
            Long id) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(reviewService.getAllReviewsForSection(id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse> deleteReview(
            @PathVariable
            Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.ok(new CustomSuccessResponse());
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomSuccessResponse<ReviewDtoResponse>> putReview(
            @PathVariable
            Long id,
            @Valid
            @RequestBody
            PutReviewDtoRequest request) {
        return ResponseEntity.ok(new CustomSuccessResponse<>(reviewService.putReview(id, request)));
    }
}
