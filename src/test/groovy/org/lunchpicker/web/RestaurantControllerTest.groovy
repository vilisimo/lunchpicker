package org.lunchpicker.web

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.domain.Restaurant
import org.lunchpicker.service.RestaurantService
import org.lunchpicker.web.request.RestaurantRequest
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus

import static org.mockito.Mockito.verify

@RunWith(MockitoJUnitRunner)
class RestaurantControllerTest {

    @Mock
    RestaurantService service

    RestaurantController restaurants

    @Before
    void setup() {
        restaurants = new RestaurantController(service)
    }

    @Test
    void "returns 200 upon requesting restaurant list"() {
        //when
        def result = restaurants.getRestaurants()

        //then
        result.statusCode == HttpStatus.OK
    }

    @Test
    void "returns 201 upon posting a restaurant"() {
        //when
        def result = restaurants.createRestaurant(new RestaurantRequest(name: "Jack's Burgers"))

        //then
        result.statusCode == HttpStatus.CREATED
    }

    @Test
    void "provides a path to a newly created resource"() {
        //given
        def restaurant = new RestaurantRequest(name: "Joey's Pizza")

        //when
        def response = restaurants.createRestaurant(restaurant)

        //then
        def captor = ArgumentCaptor.forClass(Restaurant)
        verify(service).save(captor.capture())

        def result = captor.getValue()
        response.headers.get("Location").contains(result.getId())
    }
}
