package org.lunchpicker.service;

import org.lunchpicker.domain.Restaurant;
import org.lunchpicker.persistence.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RestaurantService {

    private static final Logger logger = LoggerFactory.getLogger(RestaurantService.class);

    private final RestaurantRepository repository;

    public RestaurantService(RestaurantRepository repository) {
        this.repository = repository;
    }

    public List<Restaurant> findAll() {
        return repository.findAll();
    }

    public void save(Restaurant restaurant) {
        repository.save(restaurant);
        logger.debug("Successfully created a restaurant[id={}]", restaurant.getId());
    }
}
