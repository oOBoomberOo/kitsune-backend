package com.kitsune.backend.api;

import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<ErrorResponse> notImplemented() {
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_IMPLEMENTED)
                .message("Not implemented")
                .build()
                .toResponseEntity();
    }


    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<ErrorResponse> commonError(RuntimeException exception) {
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build()
                .toResponseEntity();
    }

    @Data
    @Builder
    public static class ErrorResponse {
        public HttpStatus code;
        public String message;

        public ResponseEntity<ErrorResponse> toResponseEntity() {
            return ResponseEntity.status(code).body(this);
        }
    }
}
