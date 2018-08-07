package org.lunchpicker.service

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.domain.Restaurant
import org.lunchpicker.persistence.RestaurantRepository
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.verify

@RunWith(MockitoJUnitRunner)
class RestaurantServiceTest {

    @Mock
    RestaurantRepository repository

    RestaurantService service

    @Before
    void setup() {
        service = new RestaurantService(repository)
    }

    @Test
    void "saves a restaurant"() {
        //given
        def restaurant = new Restaurant("Joey's Pizza")

        //when
        service.save(restaurant)

        //then
        verify(repository).save(restaurant)
    }

    @Test
    void "updates a restaurant"() {
        //given
        def restaurant = new Restaurant("Kim's Fills")

        //when
        service.update(restaurant)

        //then
        verify(repository).save(any(Restaurant))
    }

    @Test
    void "deletes a restaurant"() {
        //given
        def uuid = UUID.randomUUID() as String

        //when
        service.delete(uuid)

        //then
        verify(repository).deleteById(uuid)
    }
}