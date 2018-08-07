package org.lunchpicker.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RestaurantNotFound extends RuntimeException{

    public RestaurantNotFound(String message) {
        super(message);
    }

    public RestaurantNotFound(String message, Exception e) {
        super(message, e);
    }
}
