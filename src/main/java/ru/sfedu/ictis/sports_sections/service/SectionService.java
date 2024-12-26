package ru.sfedu.ictis.sports_sections.service;

import ru.sfedu.ictis.sports_sections.dto.request.SectionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.GetSectionDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.PagenableResponse;

import java.util.Set;

public interface SectionService {
    Long createSection(SectionDtoRequest sectionDtoRequest);

    PagenableResponse<GetSectionDtoResponse> getSections(Integer page, Integer perPage);

    PagenableResponse<GetSectionDtoResponse> findSections(
            String trainer,
            String keyword,
            String location,
            Set<String> categories,
            Integer page,
            Integer perPage);

    GetSectionDtoResponse getSectionById(Long id);

    PagenableResponse<GetSectionDtoResponse> getAllSectionsOfTrainer(Integer page, Integer perPage, Long id);

    void putSection(Long id, SectionDtoRequest section);

    void deleteSection(Long id);
}
