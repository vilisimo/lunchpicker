package org.lunchpicker.web

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.domain.Restaurant
import org.lunchpicker.domain.User
import org.lunchpicker.exceptions.InvalidUuid
import org.lunchpicker.service.RestaurantService
import org.lunchpicker.service.UserService
import org.lunchpicker.web.request.RestaurantRequest
import org.mockito.ArgumentCaptor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.http.HttpStatus

import static org.mockito.ArgumentMatchers.*
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

@RunWith(MockitoJUnitRunner)
class RestaurantControllerTest {

    @Mock
    RestaurantService restaurants

    @Mock
    UserService users

    RestaurantController controller

    @Before
    void setup() {
        controller = new RestaurantController(restaurants, users)
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
        when(restaurants.findAll()).thenReturn(data)

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
        verify(restaurants).save(captor.capture())

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
        verify(restaurants).update(captor.capture())
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
        verify(restaurants).delete(uuid)
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
        //given
        when(users.getUser(anyString())).thenReturn(new User("filler", 10))

        //when
        def response = controller.vote(UUID.randomUUID().toString(), "userId")

        //then
        response.statusCode == HttpStatus.OK
    }

    @Test
    void "voting on a restaurant instructs service layer to increment vote count"() {
        //given
        when(users.getUser(anyString())).thenReturn(new User("filler", 10))

        //when
        controller.vote(UUID.randomUUID().toString(), "userId")

        //then
        verify(restaurants).vote(anyString(), anyFloat(), anyInt())
    }

    @Test
    void "voting on a restaurant for a first time increments unique vote count"() {
        //given
        when(users.getUser(anyString())).thenReturn(new User("filler", 10))

        //when
        controller.vote(UUID.randomUUID().toString(), "userId")

        //then
        verify(restaurants).vote(anyString(), anyFloat(), eq(1))
    }

    @Test
    void "appends a vote to existing user votes"() {
        //given
        when(users.getUser(anyString())).thenReturn(new User("filler", 10))

        //when
        controller.vote(UUID.randomUUID() as String, "username")

        //then
        def captor = ArgumentCaptor.forClass(User)
        verify(users).save(captor.capture())
        def user = captor.getValue()

        user.castVotes.size() == 1
    }

    @Test
    void "decrements user's vote count upon voting"() {
        //given
        when(users.getUser(anyString())).thenReturn(new User("filler", 10))

        //when
        controller.vote(UUID.randomUUID() as String, "username")

        //then
        def captor = ArgumentCaptor.forClass(User)
        verify(users).save(captor.capture())
        def user = captor.getValue()

        user.votes == 9
    }

    @Test
    void "before voting on a restaurant validates that it exists"() {
        //given
        def uuid = UUID.randomUUID() as String
        when(users.getUser(anyString())).thenReturn(new User("filler", 10))

        //when
        controller.vote(uuid, "userId")

        //then
        verify(restaurants).exists(uuid)
    }

    @Test(expected = InvalidUuid)
    void "passing invalid UUID when voting throws exception"() {
        //when
        controller.vote("invalid", "userId")
    }

    @Test
    void "Attempting to vote with a user that does not have any votes left returns bad request"() {
        //given
        when(users.getUser(anyString())).thenReturn(new User("filler", 0))

        //when
        def response = controller.vote(UUID.randomUUID() as String, "userId")

        //then
        response.statusCode == HttpStatus.BAD_REQUEST
    }
}
