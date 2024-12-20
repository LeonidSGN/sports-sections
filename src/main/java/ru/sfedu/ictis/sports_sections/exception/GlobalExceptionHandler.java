package ru.sfedu.ictis.sports_sections.exception;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import ru.sfedu.ictis.sports_sections.dto.response.common.BaseSuccessResponse;
import ru.sfedu.ictis.sports_sections.dto.response.common.CustomSuccessResponse;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.multipart.MultipartException;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<BaseSuccessResponse> handle(ConstraintViolationException e) {
        List<Integer> codes = e.getConstraintViolations().stream()
                .map(violation -> ErrorCodes.getCodeByMessage(violation.getMessage()))
                .toList();

        List<String> messages = e.getConstraintViolations().stream()
                .map(ConstraintViolation::getMessage)
                .toList();

        return ResponseEntity.badRequest().header("errorMessage", messages.getFirst())
                .body(new BaseSuccessResponse(codes.getFirst(), codes));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    ResponseEntity handle(HttpMessageNotReadableException e) {
        return ResponseEntity.badRequest().header("errorMessage", ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getMessage())
                .body(new BaseSuccessResponse(ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getCode(),
                        List.of(ErrorCodes.HTTP_MESSAGE_NOT_READABLE_EXCEPTION.getCode())));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    ResponseEntity<BaseSuccessResponse> handle(MethodArgumentNotValidException e) {
        List<Integer> codes = e.getBindingResult().getAllErrors().stream()
                .map(objectError -> ErrorCodes.getCodeByMessage(objectError.getDefaultMessage()))
                .toList();

        List<String> messages = e.getBindingResult().getAllErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().header("errorMessage", messages.getFirst())
                .body(new BaseSuccessResponse(codes.getFirst(), codes));
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<BaseSuccessResponse> handle(HandlerMethodValidationException e) {
        List<Integer> codes = e.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .map(ErrorCodes::getCodeByMessage)
                .toList();

        List<String> messages = e.getAllErrors().stream()
                .map(MessageSourceResolvable::getDefaultMessage)
                .toList();

        return ResponseEntity.badRequest().header("errorMessage", messages.getFirst())
                .body(new BaseSuccessResponse(codes.getFirst(), codes));
    }

    @ExceptionHandler(CustomException.class)
    ResponseEntity<BaseSuccessResponse> handle(CustomException e) {
        int code = e.errorCodes.getCode();

        return ResponseEntity.badRequest().header("errorMessage", e.errorCodes.getMessage())
                .body(new BaseSuccessResponse(code, List.of(code)));
    }

    @ExceptionHandler
    public ResponseEntity<CustomSuccessResponse> handleMultipartException(MultipartException e) {
        return ResponseEntity.badRequest()
                .header("errorMessage", e.getMessage())
                .body(new CustomSuccessResponse(ErrorCodes.getCodeByMessage(e.getMessage())));
    }
}
