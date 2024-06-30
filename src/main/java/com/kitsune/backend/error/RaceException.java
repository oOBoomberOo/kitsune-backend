package com.kitsune.backend.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonIncludeProperties;
import lombok.Getter;
import lombok.Setter;
import org.springframework.http.HttpStatus;
import reactor.core.Exceptions;

import java.util.List;

@Getter
@Setter
@JsonIgnoreProperties({"cause", "stackTrace", "localizedMessage", "suppressed"})
@JsonIncludeProperties({"status", "message", "causes"})
public class RaceException extends APIException {

    List<String> causes;

    public RaceException(List<String> causes) {
        super(HttpStatus.BAD_REQUEST, causes.toString());
        this.causes = causes;
    }

    public static RaceException from(List<Throwable> exceptions) {
        var causes = exceptions.stream().map(Throwable::getMessage).toList();
        return new RaceException(causes);
    }

    public static RaceException recover(Throwable err) {
        return RaceException.from(Exceptions.unwrapMultiple(err.getCause()));
    }

}
