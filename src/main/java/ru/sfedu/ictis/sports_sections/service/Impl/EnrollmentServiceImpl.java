package ru.sfedu.ictis.sports_sections.service.Impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.EnrollDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.EnrollStatusDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.GetEnrollmentsDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.PagenableResponse;
import ru.sfedu.ictis.sports_sections.entity.EnrollmentEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.EnrollmentMapper;
import ru.sfedu.ictis.sports_sections.repository.EnrollmentRepository;
import ru.sfedu.ictis.sports_sections.repository.SectionRepository;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.EnrollmentService;

import java.util.List;

@Service
public class EnrollmentServiceImpl implements EnrollmentService {
    private final EnrollmentRepository enrollmentRepository;

    private final UserRepository userRepository;

    private final SectionRepository sectionRepository;

    private final EnrollmentMapper enrollmentMapper;

    public EnrollmentServiceImpl(EnrollmentRepository enrollmentRepository,
                                 UserRepository userRepository,
                                 SectionRepository sectionRepository,
                                 EnrollmentMapper enrollmentMapper) {
        this.enrollmentRepository = enrollmentRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.enrollmentMapper = enrollmentMapper;
    }

    @Override
    public Long createEnroll(EnrollDtoRequest enrollDtoRequest) {
        UserEntity user = userRepository
                .findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        SectionEntity section = sectionRepository
                .findById(enrollDtoRequest.getSectionId())
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        EnrollmentEntity enrollment = enrollmentMapper.toEnrollmentEntity(enrollDtoRequest, user, section);
        enrollmentRepository.save(enrollment);
        return enrollment.getId();
    }

    @Override
    public void putStatusEnroll(Long id, EnrollStatusDtoRequest enrollDtoRequest) {
        EnrollmentEntity enroll = enrollmentRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.ENROLLMENT_NOT_FOUND));

        enrollmentMapper.updateEnrollment(enroll, enrollDtoRequest);
        enrollmentRepository.save(enroll);
    }

    @Override
    public void deleteEnroll(Long id) {
        enrollmentRepository.deleteById(id);
    }

    @Override
    public PagenableResponse<GetEnrollmentsDtoResponse> getEnrollments(Integer page, Integer perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.DESC, "id"));

        Page<EnrollmentEntity> enrollmentsPage = enrollmentRepository.findAll(pageable);

        return new PagenableResponse<>(
                enrollmentsPageToListGetEnrollDto(enrollmentsPage), enrollmentsPage.getTotalElements()
        );
    }

    @Override
    public GetEnrollmentsDtoResponse getEnrollment(Long id) {
        EnrollmentEntity enrollment = enrollmentRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.ENROLLMENT_NOT_FOUND));

        GetEnrollmentsDtoResponse dto = enrollmentMapper.toGetEnrollmentsDtoResponse(enrollment);
        dto.setSectionId(enrollment.getSection().getId());
        dto.setSectionName(enrollment.getSection().getName());
        dto.setUserId(enrollment.getUser().getId());
        dto.setUserName(enrollment.getUser().getName());
        return dto;
    }

    @Override
    public PagenableResponse<GetEnrollmentsDtoResponse> getEnrollmentsForUser(Integer page, Integer perPage) {
        UserEntity user = userRepository
                .findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.DESC, "id"));

        Page<EnrollmentEntity> enrollmentsPage = enrollmentRepository.findAllByUser(user, pageable);

        return new PagenableResponse<>(
                enrollmentsPageToListGetEnrollDto(enrollmentsPage), enrollmentsPage.getTotalElements()
        );
    }

    @Override
    public PagenableResponse<GetEnrollmentsDtoResponse> getEnrollmentsForSection(
            Long sectionId,
            Integer page,
            Integer perPage) {
        SectionEntity sectionEntity = sectionRepository
                .findById(sectionId)
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.DESC, "id"));

        Page<EnrollmentEntity> enrollmentsPage = enrollmentRepository.findAllBySection(sectionEntity, pageable);

        return new PagenableResponse<>(
                enrollmentsPageToListGetEnrollDto(enrollmentsPage), enrollmentsPage.getTotalElements());
    }

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public List<GetEnrollmentsDtoResponse> enrollmentsPageToListGetEnrollDto(Page<EnrollmentEntity> enrollmentPage) {
        return enrollmentPage.getContent().stream()
                .map(enroll -> {
                    GetEnrollmentsDtoResponse dto = enrollmentMapper.toGetEnrollmentsDtoResponse(enroll);
                    dto.setSectionId(enroll.getSection().getId());
                    dto.setSectionName(enroll.getSection().getName());
                    dto.setUserId(enroll.getUser().getId());
                    dto.setUserName(enroll.getUser().getName());

                    return dto;
                })
                .toList();
    }
}
