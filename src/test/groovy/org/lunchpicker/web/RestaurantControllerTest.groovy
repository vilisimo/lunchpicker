package org.lunchpicker.web

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.domain.Restaurant
import org.lunchpicker.exceptions.InvalidUuid
import org.lunchpicker.service.RestaurantService
import org.lunchpicker.web.request.RestaurantRequest
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus

import static org.mockito.ArgumentMatchers.anyFloat
import static org.mockito.ArgumentMatchers.anyInt
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.ArgumentMatchers.eq
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

@RunWith(MockitoJUnitRunner)
class RestaurantControllerTest {

    @Mock
    RestaurantService service

    RestaurantController controller

    @Before
    void setup() {
        controller = new RestaurantController(service)
    }

    @Test
    void "returns 200 upon requesting restaurant list"() {
        //when
        def result = controller.getRestaurants()

        //then
        result.statusCode == HttpStatus.OK
    }

    @Test
    void "returns a list of restaurants"() {
        //given
        def data = [new Restaurant("Puzzle"), new Restaurant("MacLaren's")]
        when(service.findAll()).thenReturn(data)

        //when
        def result = controller.getRestaurants()

        //then
        result.body.restaurants == data
    }

    @Test
    void "returns 201 upon posting a restaurant"() {
        //when
        def result = controller.createRestaurant(new RestaurantRequest(name: "Jack's Burgers"))

        //then
        result.statusCode == HttpStatus.CREATED
    }

    @Test
    void "provides a path to a newly created resource"() {
        //given
        def restaurant = new RestaurantRequest(name: "Joey's Pizza")

        //when
        def response = controller.createRestaurant(restaurant)

        //then
        def captor = ArgumentCaptor.forClass(Restaurant)
        verify(service).save(captor.capture())

        def result = captor.getValue()
        response.headers.get("Location").contains(result.getId())
    }

    @Test
    void "patching updates a restaurant"() {
        //given
        def restaurant = new RestaurantRequest(name: "Joey's Pizza")
        def uuid = UUID.randomUUID() as String

        //when
        controller.updateRestaurant(uuid, restaurant)

        //then
        def captor = ArgumentCaptor.forClass(Restaurant)
        verify(service).update(captor.capture())
        def result = captor.getValue()

        result.id != uuid
        result.name == restaurant.name
    }

    @Test
    void "patching a restaurant returns 200"() {
        //given
        def restaurant = new RestaurantRequest(name: "Joey's Pizza")
        def uuid = UUID.randomUUID() as String

        //when
        def response = controller.updateRestaurant(uuid, restaurant)

        //then
        response.statusCode == HttpStatus.OK
    }

    @Test(expected = InvalidUuid)
    void "passing in invalid UUID throws exception"() {
        //when
        controller.updateRestaurant("invalid", new RestaurantRequest(name: "filler"))
    }

    @Test
    void "deleting a restaurant instructs deletion at a service layer"() {
        //given
        def uuid = UUID.randomUUID() as String

        //when
        controller.deleteRestaurant(uuid)

        //then
        verify(service).delete(uuid)
    }

    @Test
    void "deleting returns 204"() {
        //when
        def response = controller.deleteRestaurant(UUID.randomUUID().toString())

        //then
        response.statusCode == HttpStatus.NO_CONTENT
    }

    @Test(expected = InvalidUuid)
    void "passing in invalid UUID when deleting a restaurant throws exception"() {
        //when
        controller.deleteRestaurant("invalid")
    }

    @Test
    void "returns 200 when vote completes successfully"() {
        //when
        def response = controller.vote(UUID.randomUUID().toString(), "userId")

        //then
        response.statusCode == HttpStatus.OK
    }

    @Test
    void "voting on a restaurant instructs service layer to increment vote count"() {
        //when
        controller.vote(UUID.randomUUID().toString(), "userId")

        //then
        verify(service).vote(anyString(), anyFloat(), anyInt())
    }

    @Test(expected = InvalidUuid)
    void "passing invalid UUID when voting throws exception"() {
        //when
        controller.vote("invalid", "userId")
    }
}
