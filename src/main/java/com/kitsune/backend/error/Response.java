package com.kitsune.backend.error;

import org.springframework.http.ResponseEntity;

public interface Response<T> {
    ResponseEntity<T> toResponse();
}
