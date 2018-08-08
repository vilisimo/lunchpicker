package org.lunchpicker.service;

import org.lunchpicker.domain.Restaurant;
import org.lunchpicker.domain.Winner;
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

    private final UserService users;
    private final RestaurantService restaurants;
    private final WinnerService winners;

    public Cron(UserService users, RestaurantService restaurants, WinnerService winners) {
        this.users = users;
        this.restaurants = restaurants;
        this.winners = winners;
    }

    @Scheduled(cron = "0 0 0 * * ?")
    public void doHousework() {
        Restaurant restaurant = restaurants.selectWinner();
        Winner winner = Winner.from(restaurant);
        winners.save(winner);
        logger.info("Selected a winner[id={}] for {}", winner.getId(), winner.getDate());

        users.resetVotes();
        logger.info("User vote counts were reset");

        restaurants.resetVoteCounts();
        logger.info("Restaurant vote counts were reset");
    }
}
