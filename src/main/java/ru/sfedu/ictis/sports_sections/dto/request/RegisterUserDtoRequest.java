package ru.sfedu.ictis.sports_sections.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.sfedu.ictis.sports_sections.exception.ValidationConstants;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterUserDtoRequest {
    @NotBlank(message = ValidationConstants.USER_AVATAR_NOT_NULL)
    private String avatar;

    @Email(message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_EMAIL_NOT_VALID)
    @Size(min = 3, max = 100, message = ValidationConstants.EMAIL_SIZE_NOT_VALID)
    private String email;

    @Size(min = 3, max = 25, message = ValidationConstants.USERNAME_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_NAME_HAS_TO_BE_PRESENT)
    private String name;

    @Size(min = 6, max = 255, message = ValidationConstants.USER_PASSWORD_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_PASSWORD_NULL)
    private String password;

    @Size(min = 3, max = 25, message = ValidationConstants.ROLE_SIZE_NOT_VALID)
    @NotBlank(message = ValidationConstants.USER_ROLE_NOT_NULL)
    private String role;
}
