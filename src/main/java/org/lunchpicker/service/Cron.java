package org.lunchpicker.service;

import org.lunchpicker.persistence.RestaurantRepository;
import org.lunchpicker.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

/**
 * Class that is responsible for resetting vote counts and picking winners
 * every day.
 */
@Service
public class Cron {

    private static final Logger logger = LoggerFactory.getLogger(Cron.class);

    private final UserRepository users;
    private final RestaurantRepository restaurants;

    public Cron(UserRepository users, RestaurantRepository restaurants) {
        this.users = users;
        this.restaurants = restaurants;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void doHousework() {
        users.resetVotes();
        logger.info("User vote counts were reset");
        // todo: figure out winner (selectors?)
        restaurants.resetVoteCounts();
        logger.info("Restaurant vote counts were reset");
    }
}
