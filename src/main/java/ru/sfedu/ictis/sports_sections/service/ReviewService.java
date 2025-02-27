package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.PutReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.ReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.ReviewDtoResponse;

import java.util.List;

public interface ReviewService {
    ReviewDtoResponse addReview(ReviewDtoRequest request);

    ReviewDtoResponse getReviewUserBySession(Long sessionId);

    void deleteReview(Long id);

    List<ReviewDtoResponse> getAllReviewsForSection(Long sectionId);

    ReviewDtoResponse putReview(Long id, PutReviewDtoRequest request);
}
