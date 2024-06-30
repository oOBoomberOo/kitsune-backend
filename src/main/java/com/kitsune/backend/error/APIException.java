package com.kitsune.backend.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Getter
@Setter
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
@JsonIncludeProperties({"status", "message"})
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
