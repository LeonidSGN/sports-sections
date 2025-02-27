package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReviewDtoRequest {
    @Positive
    private Long sessionId;

    @NotNull(message = ValidationConstants.REVIEWS_RATING_NOT_VALID)
    @Min(value = 1, message = ValidationConstants.REVIEWS_RATING_NOT_VALID)
    @Max(value = 5, message = ValidationConstants.REVIEWS_RATING_NOT_VALID)
    private Integer rating;

    @NotBlank(message = ValidationConstants.REVIEWS_COMMENT_NOT_VALID)
    @Size(min = 1, max = 255, message = ValidationConstants.REVIEWS_COMMENT_NOT_VALID)
    private String comment;
}
