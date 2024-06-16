package com.kitsune.backend.api;

import com.kitsune.backend.error.APIException;
import com.kitsune.backend.error.ErrorResponse;
import org.apache.commons.lang3.NotImplementedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorHandler {

    @ExceptionHandler(APIException.class)
    public ResponseEntity<ErrorResponse> apiException(APIException exception) {
        return exception.toResponse();
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public void illegalArgument(String message) {
        throw new APIException(HttpStatus.BAD_REQUEST, message);
    }

    @ExceptionHandler(NotImplementedException.class)
    public ResponseEntity<ErrorResponse> notImplemented() {
        return ErrorResponse.builder()
                .code(HttpStatus.NOT_IMPLEMENTED)
                .message("Not implemented")
                .build()
                .toResponse();
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> commonError(RuntimeException exception) {
        return ErrorResponse.builder()
                .code(HttpStatus.INTERNAL_SERVER_ERROR)
                .message(exception.getMessage())
                .build()
                .toResponse();
    }

}
