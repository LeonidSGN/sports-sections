package ru.sfedu.ictis.sports_sections.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class LoginUserResponse {
    private Long id;

    private String avatar;

    private String email;

    private String name;

    private String role;

    private String token;

    private String theme;
}