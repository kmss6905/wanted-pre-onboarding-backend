package com.example.wanted.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Objects;

@RestControllerAdvice
@Slf4j
public class GlobalControllerAdvice {

    @ExceptionHandler(WantedException.class)
    public ResponseEntity<ErrorResponse> handleWantedException(WantedException e) {
        ErrorType errorType = e.getErrorType();
        log.info("[error] type: {}, sts: {}, msg: {}", errorType.getClassType().getSimpleName(), errorType.getHttpStatus(), errorType.getMessage());
        return ResponseEntity.status(e.getErrorType().getHttpStatus()).body(ErrorResponse.of(e.getErrorType()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        FieldError fieldError = e.getFieldError();
        Objects.requireNonNull(fieldError);

        // 첫 번째 필드 에러를 가져옵니다.
        String fieldName = fieldError.getField();             // 에러가 난 필드의 이름
        String errorMessage = fieldError.getDefaultMessage(); // 에러 메시지 (주로 Validation Annotation에서 지정한 메시지)
        log.info(String.format("MethodArgumentNotValidException for field %s: %s", fieldName, errorMessage), e);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of("E001", String.format("%s 는 %s", fieldName, errorMessage)));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ErrorResponse> handleHttpMessageNotReadableException(HttpMessageNotReadableException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse.of("E001", "잘못된 요청값입니다."));
    }

}