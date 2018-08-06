package org.lunchpicker.web;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
public class RestaurantController {

    @GetMapping(value = "/restaurants", produces = APPLICATION_JSON_VALUE)
    public ResponseEntity getRestaurants() {
        return ResponseEntity.ok().build();
    }
}
