package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.EnrollDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.request.EnrollStatusDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.GetEnrollmentsDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.PagenableResponse;

import java.util.List;

public interface EnrollmentService {
    Long createEnroll(EnrollDtoRequest enrollDtoRequest);

    void putStatusEnroll(Long id, EnrollStatusDtoRequest enrollStatusDtoRequest);

    void deleteEnroll(Long id);

    PagenableResponse<GetEnrollmentsDtoResponse> getEnrollments(Integer page, Integer perPage);

    GetEnrollmentsDtoResponse getEnrollment(Long id);

    PagenableResponse<GetEnrollmentsDtoResponse> getEnrollmentsForUser(Integer page, Integer perPage);

    PagenableResponse<GetEnrollmentsDtoResponse> getEnrollmentsForSection(Long sectionId,
                                                                          Integer page,
                                                                          Integer perPage);

    List<GetEnrollmentsDtoResponse> getListEnrollmentsForUserById(Long userId);
}
