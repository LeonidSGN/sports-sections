package ru.sfedu.ictis.sports_sections.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDtoResponse {
    private Long id;

    private Integer rating;

    private String comment;

    private LocalDateTime createAt;

    private Long userId;

    private String userName;

    private Long sectionId;
}
