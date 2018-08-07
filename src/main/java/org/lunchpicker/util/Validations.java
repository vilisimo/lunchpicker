package org.lunchpicker.util;

import java.util.UUID;

public final class Validations {

    private Validations() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static void isUuid(String uuid) {
        UUID.fromString(uuid);
    }
}
