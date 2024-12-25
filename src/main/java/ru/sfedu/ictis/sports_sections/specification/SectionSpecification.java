package ru.sfedu.ictis.sports_sections.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;

import java.util.Set;

public class SectionSpecification {
    public static Specification<SectionEntity> hasTrainerName(String trainerName) {
        return (root, query, criteriaBuilder) -> {
            if (trainerName == null || trainerName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("trainer").get("name")),
                    "%" + trainerName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<SectionEntity> hasSectionName(String sectionName) {
        return (root, query, criteriaBuilder) -> {
            if (sectionName == null || sectionName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("name")),
                    "%" + sectionName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<SectionEntity> hasLocation(String location) {
        return (root, query, criteriaBuilder) -> {
            if (location == null || location.isEmpty()) {
                return criteriaBuilder.conjunction();
            }
            return criteriaBuilder.like(
                    criteriaBuilder.lower(root.get("location")),
                    "%" + location.toLowerCase() + "%"
            );
        };
    }

    public static Specification<SectionEntity> hasCategories(Set<String> categoriesName) {
        return (root, query, criteriaBuilder) -> {
            if (categoriesName == null || categoriesName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<SectionEntity, CategoryEntity> categories = root.join("categoryEntities");

            Predicate[] categoryPredicates = categoriesName.stream()
                    .map(name -> criteriaBuilder.equal(categories.get("name"), name))
                    .toArray(Predicate[]::new);

            Predicate categoryPredicate = criteriaBuilder.or(categoryPredicates);

            query.distinct(true);

            return categoryPredicate;
        };
    }
}
