package org.lunchpicker.service;

import org.lunchpicker.configuration.UserProperties;
import org.lunchpicker.domain.User;
import org.lunchpicker.persistence.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Class responsible for manipulating User model.
 *
 * Typically, methods would be a bit more elaborate, and it would make more
 * sense to centralize operations in one class rather than code to repository
 * interface directly.
 */
@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    private final UserProperties properties;
    private final UserRepository users;

    public UserService(UserProperties properties, UserRepository users) {
        this.properties = properties;
        this.users = users;
    }

    public User getUser(String username) {
        return users.findById(username)
                .orElseGet(() -> users.save(new User(username, properties.getVotes())));
    }

    public void save(User user) {
        users.save(user);
        logger.debug("Successfully saved a user[username={}]", user.getUsername());
    }

    void resetVotes() {
        users.resetVotes();
        logger.debug("Successfully reset votes for all users");
    }
}
