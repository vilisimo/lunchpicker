package org.lunchpicker.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * A class that holds configuration properties related to user.
 *
 * Helps to avoid scattering {@code @Value("${lunchpicker.user.property}")}
 * throughout the code base, as well as makes mocking easier.
 */
@Component
@Validated
@ConfigurationProperties(prefix = "lunchpicker.user")
public class UserProperties {

    @NotNull
    private int votes;

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }
}
