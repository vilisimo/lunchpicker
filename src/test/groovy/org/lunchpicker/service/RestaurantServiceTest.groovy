package org.lunchpicker.service

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.domain.Restaurant
import org.lunchpicker.exceptions.RestaurantNotFound
import org.lunchpicker.persistence.RestaurantRepository
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyFloat
import static org.mockito.ArgumentMatchers.anyInt
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

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

    @Test
    void "votes on a restaurant"() {
        //given
        def uuid = UUID.randomUUID() as String
        when(repository.vote(anyString(), anyFloat(), anyInt())).thenReturn(1)

        //when
        service.vote(uuid, 1.0, 1)

        //then
        verify(repository).vote(uuid, 1.0, 1)
    }

    @Test(expected = RestaurantNotFound)
    void "when no tables are updated, throws restaurant not found exception"() {
        //given
        when(repository.vote(anyString(), anyFloat(), anyInt())).thenReturn(0)

        //when
        service.vote(UUID.randomUUID() as String, 1.0, 1)
    }
}