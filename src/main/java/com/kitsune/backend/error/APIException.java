package com.kitsune.backend.error;

import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
public class APIException extends RuntimeException implements Response<ErrorResponse> {
    HttpStatus status;
    String message;

    public APIException(HttpStatus status, String message) {
        super(message);
        this.status = status;
        this.message = message;
    }

    public ResponseEntity<ErrorResponse> toResponse() {
        return new ErrorResponse(status, message).toResponse();
    }
}
