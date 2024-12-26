package ru.sfedu.ictis.sports_sections.service.Impl;

import jakarta.transaction.Transactional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import ru.sfedu.ictis.sports_sections.dto.request.SectionDtoRequest;
import ru.sfedu.ictis.sports_sections.dto.response.CategoryDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.GetSectionDtoResponse;
import ru.sfedu.ictis.sports_sections.dto.response.PagenableResponse;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.CategoryMapper;
import ru.sfedu.ictis.sports_sections.mapper.SectionMapper;
import ru.sfedu.ictis.sports_sections.repository.CategoryRepository;
import ru.sfedu.ictis.sports_sections.repository.SectionRepository;
import ru.sfedu.ictis.sports_sections.repository.UserRepository;
import ru.sfedu.ictis.sports_sections.service.SectionService;
import ru.sfedu.ictis.sports_sections.specification.SectionSpecification;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Transactional
public class SectionServiceImpl implements SectionService {
    private final SectionRepository sectionRepository;

    private final UserRepository userRepository;

    private final CategoryRepository categoryRepository;

    private final SectionMapper sectionMapper;

    private final CategoryMapper categoryMapper;

    public SectionServiceImpl(SectionRepository sectionRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository,
                              SectionMapper sectionMapper,
                              CategoryMapper categoryMapper) {
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.sectionMapper = sectionMapper;
        this.categoryMapper = categoryMapper;
    }

    @Override
    public Long createSection(SectionDtoRequest sectionDtoRequest) {
        UserEntity trainer = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!trainer.getRole().equals("trainer")) {
            throw new CustomException(ErrorCodes.NO_RIGHTS_FOR_CREATE_SECTION);
        }

        SectionEntity sectionEntity = sectionMapper.toSectionEntity(sectionDtoRequest);
        sectionEntity.setTrainer(trainer);

        Set<CategoryEntity> categoryEntities = addAndGetAllCategories(sectionDtoRequest.getCategories());
        sectionEntity.setCategoryEntities(categoryEntities);
        sectionRepository.save(sectionEntity);
        return sectionEntity.getId();
    }

    @Override
    public PagenableResponse<GetSectionDtoResponse> getSections(Integer page, Integer perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.DESC, "id"));

        Page<SectionEntity> sectionPage = sectionRepository.findAll(pageable);

        return new PagenableResponse<>(sectionsPageToListGetSectionDto(sectionPage), sectionPage.getTotalElements());
    }

    @Override
    public PagenableResponse<GetSectionDtoResponse> findSections(String trainer,
                                          String keywords,
                                          String location,
                                          Set<String> categories,
                                          Integer page,
                                          Integer perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.DESC, "id"));

        Specification<SectionEntity> spec = Specification.where(SectionSpecification.hasTrainerName(trainer))
                .and(SectionSpecification.hasSectionName(keywords))
                .and(SectionSpecification.hasLocation(location))
                .and(SectionSpecification.hasCategories(categories));

        Page<SectionEntity> sectionPage = sectionRepository.findAll(spec, pageable);

        return new PagenableResponse<>(sectionsPageToListGetSectionDto(sectionPage), sectionPage.getTotalElements());
    }

    @Override
    public GetSectionDtoResponse getSectionById(Long id) {
        SectionEntity sectionEntity = sectionRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        GetSectionDtoResponse res = sectionMapper.toGetSectionDtoResponse(sectionEntity);
        res.setTrainerName(sectionEntity.getTrainer().getName());
        res.setTrainerId(sectionEntity.getTrainer().getId());

        Set<CategoryDtoResponse> categoriesList = sectionEntity.getCategoryEntities()
                .stream()
                .map(categoryMapper::toCategoryDtoResponse)
                .collect(Collectors.toSet());

        res.setCategories(categoriesList);

        return res;
    }

    @Override
    public PagenableResponse<GetSectionDtoResponse> getAllSectionsOfTrainer(Integer page, Integer perPage, Long id) {
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.DESC, "id"));

        UserEntity trainer = userRepository.findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        Page<SectionEntity> sectionsPage = sectionRepository.findAllByTrainer(pageable, trainer);

        return new PagenableResponse<>(sectionsPageToListGetSectionDto(sectionsPage), sectionsPage.getTotalElements());
    }

    @Override
    public void putSection(Long id, SectionDtoRequest section) {
        SectionEntity sectionEntity = sectionRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        sectionMapper.updateSectionFromDto(section, sectionEntity);

        sectionEntity.setCategoryEntities(addAndGetAllCategories(section.getCategories()));

        sectionRepository.save(sectionEntity);
    }

    @Override
    public void deleteSection(Long id) {
        sectionRepository.deleteById(id);
    }

    public String getCurrentUserEmail() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    public Set<CategoryEntity> addAndGetAllCategories(Set<String> requestedCategories) {
        Set<CategoryEntity> existingCategories = new HashSet<>(categoryRepository.findAllByNameIn(requestedCategories));

        Set<String> existingCategoryName = categoryMapper.toCategoryNames(existingCategories);

        Set<CategoryEntity> newCategories = requestedCategories.stream()
                .filter(category -> !existingCategoryName.contains(category))
                .map(name -> {
                    CategoryEntity newCategory = new CategoryEntity();
                    newCategory.setName(name);
                    return newCategory;
                }).collect(Collectors.toSet());

        if (!newCategories.isEmpty()) {
            categoryRepository.saveAll(newCategories);
            existingCategories.addAll(newCategories);
        }

        return existingCategories;
    }

    public List<GetSectionDtoResponse> sectionsPageToListGetSectionDto(Page<SectionEntity> sectionsPage) {
        return sectionsPage.getContent().stream()
                .map(section -> {
                    GetSectionDtoResponse dto = sectionMapper.toGetSectionDtoResponse(section);
                    dto.setTrainerName(section.getTrainer().getName());
                    dto.setTrainerId(section.getTrainer().getId());

                    Set<CategoryDtoResponse> categoriesList = section.getCategoryEntities()
                            .stream()
                            .map(categoryMapper::toCategoryDtoResponse)
                            .collect(Collectors.toSet());

                    dto.setCategories(categoriesList);

                    return dto;
                })
                .toList();
    }
}
