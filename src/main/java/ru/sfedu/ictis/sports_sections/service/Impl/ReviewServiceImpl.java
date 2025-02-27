package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.PutReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.ReviewDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.ReviewDtoResponse;
import ru.sfedu.ictis.sports_sections.entity.ReviewEntity;
import ru.sfedu.ictis.sports_sections.entity.SessionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.ReviewMapper;
import ru.sfedu.ictis.sports_sections.repository.EnrollmentRepository;
import ru.sfedu.ictis.sports_sections.repository.ReviewRepository;
import ru.sfedu.ictis.sports_sections.repository.SessionRepository;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.ReviewService;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewServiceImpl implements ReviewService {
    private final UserRepository userRepository;

    private final SessionRepository sessionRepository;

    private final EnrollmentRepository enrollmentRepository;

    private final ReviewMapper reviewMapper;

    private final ReviewRepository reviewRepository;

    public ReviewServiceImpl(UserRepository userRepository,
                             SessionRepository sessionRepository,
                             EnrollmentRepository enrollmentRepository,
                             ReviewMapper reviewMapper, ReviewRepository reviewRepository) {
        this.userRepository = userRepository;
        this.sessionRepository = sessionRepository;
        this.enrollmentRepository = enrollmentRepository;
        this.reviewMapper = reviewMapper;
        this.reviewRepository = reviewRepository;
    }

    @Override
    public ReviewDtoResponse addReview(ReviewDtoRequest request) {
        UserEntity user = userRepository
                .findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        SessionEntity session = sessionRepository
                .findById(request.getSessionId())
                .orElseThrow(() -> new CustomException(ErrorCodes.SESSION_NOT_FOUND));

        boolean register = enrollmentRepository.existsByUserIdAndSectionIdAndStatus(
                        user.getId(),
                        session.getSection().getId(),
                        "register");

        if (!register) {
            throw new CustomException(ErrorCodes.NO_RIGHT_FOR_CREATE_REVIEW);
        }

        if (reviewRepository.existsByUserIdAndSessionId(user.getId(), session.getId())) {
            throw new CustomException(ErrorCodes.REVIEW_EXISTS);
        }

        ReviewEntity reviewEntity = reviewMapper.toReviewEntity(request, user, session);
        reviewEntity.setCreateAt(LocalDateTime.now());
        reviewRepository.save(reviewEntity);
        ReviewDtoResponse response = reviewMapper.toReviewDtoResponse(reviewEntity);
        response.setUserId(user.getId());
        response.setUserName(user.getName());
        response.setSessionId(session.getId());
        return response;
    }

    @Override
    public ReviewDtoResponse getReviewUserBySession(Long sessionId) {
        UserEntity user = userRepository
                .findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        Optional<ReviewEntity> review = reviewRepository.findByUserIdAndSessionId(user.getId(), sessionId);
        return review.map(r -> {
            ReviewDtoResponse response = reviewMapper.toReviewDtoResponse(r);
            response.setUserId(r.getUser().getId());
            response.setUserName(r.getUser().getName());
            response.setSessionId(r.getSession().getId());
            return response;
        }).orElseThrow(() -> new CustomException(ErrorCodes.REVIEW_NOT_FOUND));
    }

    @Override
    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    @Override
    public List<ReviewDtoResponse> getAllReviewsForSection(Long sectionId) {
        List<ReviewEntity> reviews = reviewRepository.findBySession_Section_Id(sectionId);

        return reviews.stream()
                .map(review -> {
                    ReviewDtoResponse response = reviewMapper.toReviewDtoResponse(review);
                    response.setUserId(review.getUser().getId());
                    response.setUserName(review.getUser().getName());
                    response.setSessionId(review.getSession().getId());
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
        response.setSessionId(review.getSession().getId());

        return response;
    }

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }
}
