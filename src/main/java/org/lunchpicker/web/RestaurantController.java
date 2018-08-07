package org.lunchpicker.web;

import org.lunchpicker.domain.Restaurant;
import org.lunchpicker.service.RestaurantService;
import org.lunchpicker.util.Validations;
import org.lunchpicker.web.request.RestaurantRequest;
import org.lunchpicker.web.response.RestaurantsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/restaurants")
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

    @PatchMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity updateRestaurant(@PathVariable String id, @RequestBody RestaurantRequest body) {
        Restaurant restaurant = new Restaurant(id, body.name);
        service.update(restaurant);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable String id) {
        Validations.isUuid(id);
        service.delete(id);

        return ResponseEntity.noContent().build();
    }
}
