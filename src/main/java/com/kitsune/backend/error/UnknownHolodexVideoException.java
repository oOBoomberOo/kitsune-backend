package com.kitsune.backend.error;

import org.springframework.http.HttpStatus;

public class UnknownHolodexVideoException extends APIException {
    public UnknownHolodexVideoException(String videoId) {
        super(HttpStatus.NOT_FOUND, "Unknown Holodex video: " + videoId);
    }
}
