package org.lunchpicker.util;

import org.lunchpicker.exceptions.InvalidUuid;

import java.util.UUID;

public final class Validations {

    private Validations() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static void isUuid(String uuid) {
        try {
            UUID.fromString(uuid);
        } catch (IllegalArgumentException e) {
            throw new InvalidUuid(uuid + " is not a valid UUID", e);
        }
    }
}
