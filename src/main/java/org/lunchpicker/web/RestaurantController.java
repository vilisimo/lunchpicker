package org.lunchpicker.web;

import org.lunchpicker.domain.Restaurant;
import org.lunchpicker.domain.User;
import org.lunchpicker.domain.Vote;
import org.lunchpicker.service.RestaurantService;
import org.lunchpicker.service.UserService;
import org.lunchpicker.util.Scales;
import org.lunchpicker.util.Validations;
import org.lunchpicker.web.request.RestaurantRequest;
import org.lunchpicker.web.response.ErrorResponse;
import org.lunchpicker.web.response.RestaurantsResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(path = "/restaurants")
public class RestaurantController {

    private final RestaurantService restaurants;
    private final UserService users;

    public RestaurantController(RestaurantService restaurants, UserService users) {
        this.restaurants = restaurants;
        this.users = users;
    }

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    public ResponseEntity<RestaurantsResponse> getRestaurants() {
        List<Restaurant> restaurants = this.restaurants.findAll();

        return ResponseEntity.ok(new RestaurantsResponse(restaurants));
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity createRestaurant(@RequestBody RestaurantRequest body) {
        Restaurant restaurant = new Restaurant(body.name);
        restaurants.save(restaurant);

        return ResponseEntity.created(URI.create(restaurant.getId())).build();
    }

    @PatchMapping(path = "/{id}", consumes = APPLICATION_JSON_VALUE)
    public ResponseEntity updateRestaurant(@PathVariable String id, @RequestBody RestaurantRequest body) {
        Restaurant restaurant = new Restaurant(id, body.name);
        restaurants.update(restaurant);

        return ResponseEntity.ok().build();
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity deleteRestaurant(@PathVariable String id) {
        Validations.isUuid(id);
        restaurants.delete(id);

        return ResponseEntity.noContent().build();
    }

    @PostMapping(path = "/{id}:vote")
    public ResponseEntity vote(@PathVariable String id, @RequestHeader("username") String username) {
        Validations.isUuid(id);
        restaurants.exists(id);

        User user = users.getUser(username);
        if (user.getVotes() <= 0) {
            return ResponseEntity.badRequest().body(new ErrorResponse("User does not have any votes left"));
        }

        float weight = Scales.weighUserVote(user, id);
        int unique = weight == 1.0 ? 1 : 0;

        user.addVote(new Vote(id));
        users.save(user);

        restaurants.vote(id, weight, unique);

        return ResponseEntity.ok().build();
    }
}
