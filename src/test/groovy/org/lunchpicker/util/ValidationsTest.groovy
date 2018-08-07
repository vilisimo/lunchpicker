package org.lunchpicker.util

import org.junit.Test
import org.lunchpicker.exceptions.InvalidUuid

class ValidationsTest {

    @Test
    void "recognizes valid UUID strings"() {
        Validations.isUuid(UUID.randomUUID().toString())
    }

    @Test(expected = InvalidUuid)
    void "catches invalid UUID strings"() {
        Validations.isUuid("not UUID")
    }
}