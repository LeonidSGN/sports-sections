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
import ru.sfedu.ictis.sports_sections.dto.response.UserResponse;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;
import ru.sfedu.ictis.sports_sections.entity.LocationEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;
import ru.sfedu.ictis.sports_sections.exception.CustomException;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;
import ru.sfedu.ictis.sports_sections.mapper.CategoryMapper;
import ru.sfedu.ictis.sports_sections.mapper.LocationMapper;
import ru.sfedu.ictis.sports_sections.mapper.SectionMapper;
import ru.sfedu.ictis.sports_sections.mapper.UserMapper;
import ru.sfedu.ictis.sports_sections.repository.CategoryRepository;
import ru.sfedu.ictis.sports_sections.repository.LocationRepository;
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

    private final LocationRepository locationRepository;

    private final SectionMapper sectionMapper;

    private final CategoryMapper categoryMapper;

    private final UserMapper userMapper;

    private final LocationMapper locationMapper;

    public SectionServiceImpl(SectionRepository sectionRepository,
                              UserRepository userRepository,
                              CategoryRepository categoryRepository, LocationRepository locationRepository,
                              SectionMapper sectionMapper,
                              CategoryMapper categoryMapper, UserMapper userMapper, LocationMapper locationMapper) {
        this.sectionRepository = sectionRepository;
        this.userRepository = userRepository;
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
        this.sectionMapper = sectionMapper;
        this.categoryMapper = categoryMapper;
        this.userMapper = userMapper;
        this.locationMapper = locationMapper;
    }

    @Override
    public Long createSection(SectionDtoRequest sectionDtoRequest) {
        UserEntity admin = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!admin.getRole().equals("admin")) {
            throw new CustomException(ErrorCodes.NO_RIGHTS_FOR_CREATE);
        }

        LocationEntity location = locationRepository.findById(sectionDtoRequest.getLocationId())
                .orElseThrow(() -> new CustomException(ErrorCodes.LOCATION_NOT_EXISTS));

        if (sectionRepository.existsByNameAndLocation(sectionDtoRequest.getName(), location)) {
            throw new CustomException(ErrorCodes.SECTION_EXISTS);
        }

        SectionEntity sectionEntity = sectionMapper.toSectionEntity(sectionDtoRequest);
        sectionEntity.setLocation(location);

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
    public PagenableResponse<GetSectionDtoResponse> getSectionsWithTrainer(Integer page, Integer perPage) {
        Pageable pageable = PageRequest.of(page - 1, perPage, Sort.by(Sort.Direction.DESC, "id"));

        Page<SectionEntity> sectionPage = sectionRepository.findAllWithTrainers(pageable);

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
                .and(SectionSpecification.hasCategories(categories))
                .and(SectionSpecification.hasTrainers());

        Page<SectionEntity> sectionPage = sectionRepository.findAll(spec, pageable);

        return new PagenableResponse<>(sectionsPageToListGetSectionDto(sectionPage), sectionPage.getTotalElements());
    }

    @Override
    public GetSectionDtoResponse getSectionById(Long id) {
        SectionEntity sectionEntity = sectionRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        GetSectionDtoResponse res = sectionMapper.toGetSectionDtoResponse(sectionEntity);

        res.setLocation(locationMapper.toLocationResponseDto(sectionEntity.getLocation()));

        Set<UserResponse> trainersList = sectionEntity.getTrainers()
                .stream()
                .map(userMapper::toUserResponse)
                .collect(Collectors.toSet());

        res.setTrainers(trainersList);

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

        Page<SectionEntity> sectionsPage = sectionRepository.findAllByTrainersContaining(pageable, trainer);

        return new PagenableResponse<>(sectionsPageToListGetSectionDto(sectionsPage), sectionsPage.getTotalElements());
    }

    @Override
    public void putSection(Long id, SectionDtoRequest section) {
        UserEntity admin = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!admin.getRole().equals("admin")) {
            throw new CustomException(ErrorCodes.NO_RIGHT);
        }

        SectionEntity sectionEntity = sectionRepository
                .findById(id)
                .orElseThrow(() -> new CustomException(ErrorCodes.SECTION_NOT_FOUND));

        sectionMapper.updateSectionFromDto(section, sectionEntity);

        sectionEntity.setCategoryEntities(addAndGetAllCategories(section.getCategories()));

        sectionRepository.save(sectionEntity);
    }

    @Override
    public void deleteSection(Long id) {
        UserEntity admin = userRepository.findByEmail(getCurrentUserEmail())
                .orElseThrow(() -> new CustomException(ErrorCodes.USER_NOT_FOUND));

        if (!admin.getRole().equals("admin")) {
            throw new CustomException(ErrorCodes.NO_RIGHT);
        }

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

                    dto.setLocation(locationMapper.toLocationResponseDto(section.getLocation()));

                    Set<UserResponse> trainersList = section.getTrainers()
                            .stream()
                            .map(userMapper::toUserResponse)
                            .collect(Collectors.toSet());

                    dto.setTrainers(trainersList);

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
