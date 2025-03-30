package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.PutReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.ReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.ReviewDtoResponse;
import ru.sfedu.ictis.sports_sections.entity.ReviewEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.ReviewMapper;
import ru.sfedu.ictis.sports_sections.repository.EnrollmentRepository;
import ru.sfedu.ictis.sports_sections.repository.ReviewRepository;
import ru.sfedu.ictis.sports_sections.repository.SectionRepository;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final UserRepository userRepository;

    private final SectionRepository sectionRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final ReviewMapper reviewMapper;

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(UserRepository userRepository,
                             SectionRepository sectionRepository,
                             EnrollmentRepository enrollmentRepository,
                             ReviewMapper reviewMapper, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDtoResponse addReview(ReviewDtoRequest request) {
        UserEntity user = userRepository
                .findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        SectionEntity section = sectionRepository
                .findById(request.getSectionId())
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        boolean register = enrollmentRepository.existsByUserIdAndSectionIdAndStatus(
                        user.getId(),
                        section.getId(),
                        "register");

        if (!register) {
            throw new CustomException(ErrorCodes.NO_RIGHT_FOR_CREATE_REVIEW);
        }

        if (reviewRepository.existsByUserIdAndSectionId(user.getId(), section.getId())) {
            throw new CustomException(ErrorCodes.REVIEW_EXISTS);
        }

        ReviewEntity reviewEntity = reviewMapper.toReviewEntity(request, user, section);
        reviewEntity.setCreateAt(LocalDateTime.now());
        reviewRepository.save(reviewEntity);
        ReviewDtoResponse response = reviewMapper.toReviewDtoResponse(reviewEntity);
        response.setUserId(user.getId());
        response.setUserName(user.getName());
        response.setSectionId(section.getId());
        return response;
    }

    @Override
    public ReviewDtoResponse getReviewUserBySection(Long sectionId) {
        UserEntity user = userRepository
                .findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        Optional<ReviewEntity> review = reviewRepository.findByUserIdAndSectionId(user.getId(), sectionId);
        return review.map(r -> {
            ReviewDtoResponse response = reviewMapper.toReviewDtoResponse(r);
            response.setUserId(r.getUser().getId());
            response.setUserName(r.getUser().getName());
            response.setSectionId(r.getSection().getId());
            return response;
        }).orElseThrow(() -> new CustomException(ErrorCodes.REVIEW_NOT_FOUND));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewDtoResponse> getAllReviewsForSection(Long sectionId) {
        List<ReviewEntity> reviews = reviewRepository.findBySectionId(sectionId);

        return reviews.stream()
                .map(review -> {
                    ReviewDtoResponse response = reviewMapper.toReviewDtoResponse(review);
                    response.setUserId(review.getUser().getId());
                    response.setUserName(review.getUser().getName());
                    response.setSectionId(review.getSection().getId());
                    return response;
                })
                .toList();
    }

    @Override
    public ReviewDtoResponse putReview(Long id, PutReviewDtoRequest request) {
        ReviewEntity review = reviewRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.REVIEW_NOT_FOUND));

        reviewMapper.updateReviewFromDto(request, review);
        reviewRepository.save(review);

        ReviewDtoResponse response = reviewMapper.toReviewDtoResponse(review);
        response.setUserId(review.getUser().getId());
        response.setUserName(review.getUser().getName());
        response.setSectionId(review.getSection().getId());

        return response;
    }

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
