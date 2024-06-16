package com.kitsune.backend.error;

import lombok.Builder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

@Builder
public record ErrorResponse(HttpStatus code, String message) implements Response<ErrorResponse> {
    @Override
    public ResponseEntity<ErrorResponse> toResponse() {
        return ResponseEntity.status(code).body(this);
    }
}
