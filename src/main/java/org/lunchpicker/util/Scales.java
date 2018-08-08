package org.lunchpicker.util;

import org.lunchpicker.domain.User;

public final class Scales {

    private Scales() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static float weighUserVote(User user, String restaurantId) {
        long similarVotes = user.getCastVotes().stream()
                .filter(v -> v.getRestaurantId().equals(restaurantId))
                .count();

        if (similarVotes == 0) {
            return 1f;
        } else if (similarVotes == 1) {
            return 0.5f;
        }
        return 0.25f;

    }
}
