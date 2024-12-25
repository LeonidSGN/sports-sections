package ru.sfedu.ictis.sports_sections.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;
import ru.sfedu.ictis.sports_sections.dto.response.CategoryDtoResponse;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;

import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface CategoryMapper {
    default String map(CategoryEntity categoryEntity) {
        return categoryEntity.getName();
    }

    Set<String> toCategoryNames(Set<CategoryEntity> categoryEntities);

    CategoryDtoResponse toCategoryDtoResponse(CategoryEntity categoryEntity);
}
