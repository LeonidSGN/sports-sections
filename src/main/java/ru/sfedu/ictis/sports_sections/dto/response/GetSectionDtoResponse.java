package ru.sfedu.ictis.sports_sections.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GetSectionDtoResponse {
    private Long id;

    private String name;

    private String description;

    private String location;

    private Long trainerId;

    private String trainerName;

    private Set<CategoryDtoResponse> categories;
}
