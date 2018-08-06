package org.lunchpicker.web

import org.junit.Test
import org.springframework.http.HttpStatus

class RestaurantControllerTest {

    def restaurants = new RestaurantController()

    @Test
    void "returns 200 upon requesting restaurant list"() {
        //when
        def result = restaurants.getRestaurants()

        //then
        result.statusCode == HttpStatus.OK
    }
}
