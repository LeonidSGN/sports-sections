package ru.sfedu.ictis.sports_sections.specification;

import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import ru.sfedu.ictis.sports_sections.entity.CategoryEntity;
import ru.sfedu.ictis.sports_sections.entity.LocationEntity;
import ru.sfedu.ictis.sports_sections.entity.SectionEntity;
import ru.sfedu.ictis.sports_sections.entity.UserEntity;

import java.util.Set;

public class SectionSpecification {
    public static Specification<SectionEntity> hasTrainerName(String trainerName) {
        return (root, query, criteriaBuilder) -> {
            if (trainerName == null || trainerName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<SectionEntity, UserEntity> trainers = root.join("trainers");
            return criteriaBuilder.like(
                    criteriaBuilder.lower(trainers.get("name")),
                    "%" + trainerName.toLowerCase() + "%"
            );
        };
    }

    public static Specification<SectionEntity> hasTrainers() {
        return (root, query, criteriaBuilder) -> {
            Join<SectionEntity, UserEntity> trainers = root.join("trainers", JoinType.INNER);
            return criteriaBuilder.isNotEmpty(root.get("trainers"));
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

    public static Specification<SectionEntity> hasLocation(String locationName) {
        return (root, query, criteriaBuilder) -> {
            if (locationName == null || locationName.isEmpty()) {
                return criteriaBuilder.conjunction();
            }

            Join<SectionEntity, LocationEntity> location = root.join("location");
            return criteriaBuilder.like(
                    criteriaBuilder.lower(location.get("fullLocation")),
                    "%" + locationName.toLowerCase() + "%"
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
