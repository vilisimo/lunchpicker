package org.lunchpicker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidUuid extends RuntimeException {
    public InvalidUuid(String message, Exception e) {
        super(message, e);
    }
}
