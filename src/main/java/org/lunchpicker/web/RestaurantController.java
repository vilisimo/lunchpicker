package org.lunchpicker.web;

import org.lunchpicker.domain.Restaurant;
import org.lunchpicker.service.RestaurantService;
import org.lunchpicker.web.request.RestaurantRequest;
import org.lunchpicker.web.response.RestaurantsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController(value = "/restaurants")
public class RestaurantController {

    private final RestaurantService service;

    public RestaurantController(RestaurantService service) {
        this.service = service;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantsResponse> getRestaurants() {
        List<Restaurant> restaurants = service.findAll();

        return ResponseEntity.ok(new RestaurantsResponse(restaurants));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity createRestaurant(@RequestBody RestaurantRequest body) {
        Restaurant restaurant = new Restaurant(body.name);
        service.save(restaurant);

        return ResponseEntity.created(URI.create(restaurant.getId())).build();
    }
}
