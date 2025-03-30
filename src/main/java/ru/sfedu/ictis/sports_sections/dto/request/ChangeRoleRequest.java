package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

@Getter
@Setter
public class ChangeRoleRequest {
    @Size(min = 3, max = 25, message = ValidationConstants.ROLE_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_ROLE_NOT_NULL)
    private String role;
}
