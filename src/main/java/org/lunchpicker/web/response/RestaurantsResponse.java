package org.lunchpicker.web.response;

import org.lunchpicker.domain.Restaurant;

import java.util.Collections;
import java.util.List;

public class RestaurantsResponse {

    private final int total;
    private final List<Restaurant> restaurants;

    public RestaurantsResponse(List<Restaurant> restaurants) {
        this.total = restaurants.size();
        this.restaurants = Collections.unmodifiableList(restaurants);
    }

    public int getTotal() {
        return total;
    }

    public List<Restaurant> getRestaurants() {
        return restaurants;
    }
}
