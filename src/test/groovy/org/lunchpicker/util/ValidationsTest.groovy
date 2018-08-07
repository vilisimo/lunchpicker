package org.lunchpicker.util

import org.junit.Test

class ValidationsTest {

    @Test
    void "recognizes valid UUID strings"() {
        Validations.isUuid(UUID.randomUUID().toString())
    }

    @Test(expected = IllegalArgumentException)
    void "catches invalid UUID strings"() {
        Validations.isUuid("not UUID")
    }
}