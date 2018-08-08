package org.lunchpicker.service;

import org.lunchpicker.domain.Restaurant;
import org.lunchpicker.exceptions.RestaurantNotFound;
import org.lunchpicker.persistence.RestaurantRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Class responsible for operations/manipulations on a restaurant.
 *
 * Typically methods would be a bit more complicated, and, in some cases
 * (e.g., {@link #exists(String)}), a bit more general rather than quick and
 * dirty ways of achieving desired functionality for specific client
 * (in this case, controller).
 */
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

    public void update(Restaurant restaurant) {
        repository.save(restaurant);
        logger.debug("Successfully updated a restaurant[id={}]", restaurant.getId());
    }

    public void delete(String id) {
        try {
            repository.deleteById(id);
        } catch (EmptyResultDataAccessException e) {
            logger.debug("Attempt to delete a restaurant[id={}] that does not exist was registered", id);
            throw new RestaurantNotFound("Restaurant[id=" + id + "] does not exist", e);
        }
        logger.debug("Successfully deleted a restaurant[id={}]", id);
    }

    public void exists(String id) {
        if(!repository.existsById(id)) {
            logger.debug("Attempt to manipulate a restaurant[id={}] that does not exist was registered", id);
            throw new RestaurantNotFound("Restaurant with id " + id + " does not exist");
        }
    }

    public void vote(String id, float weight, int unique) {
        int result = repository.vote(id, weight, unique);

        if (result == 0) {
            logger.debug("Attempt to vote on a restaurant[id={}] that does not exist was registered", id);
            throw new RestaurantNotFound("Restaurant with id " + id + " does not exist");
        }

        logger.debug("Successfully voted for a restaurant[id={}], +{}", id, weight);
    }
}
