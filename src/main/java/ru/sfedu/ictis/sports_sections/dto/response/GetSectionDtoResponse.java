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

    private String avatar;

    private String name;

    private String description;

    private LocationResponseDto location;

    private Set<UserResponse> trainers;

    private Set<CategoryDtoResponse> categories;
}
