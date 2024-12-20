package ru.sfedu.ictis.sports_sections.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;
import ru.sfedu.ictis.sports_sections.dto.response.common.BaseSuccessResponse;
import ru.sfedu.ictis.sports_sections.exception.ErrorCodes;

import java.io.IOException;
import java.util.List;

@Component
public class CustomEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request,
                         HttpServletResponse response,
                         AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        int errorCode = ErrorCodes.UNAUTHORISED.getCode();

        BaseSuccessResponse customResponse = new BaseSuccessResponse(
                errorCode,
                List.of(errorCode)
        );

        ObjectMapper objectMapper = new ObjectMapper();
        String jsonResponse = objectMapper.writeValueAsString(customResponse);

        response.getWriter().write(jsonResponse);
        response.getWriter().flush();
    }
}