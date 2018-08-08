package org.lunchpicker.service

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.domain.Restaurant
import org.lunchpicker.domain.Winner
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

@RunWith(MockitoJUnitRunner)
class CronTest {

    @Mock
    UserService users

    @Mock
    RestaurantService restaurants

    @Mock
    WinnerService winners

    Cron cron

    @Before
    void setup() {
        cron = new Cron(users, restaurants, winners)
    }

    @Test
    void "clears user votes"() {
        //given
        when(restaurants.selectWinner()).thenReturn(new Restaurant(UUID.randomUUID() as String, "rhubarbar"))

        //when
        cron.doHousework()

        //then
        verify(users).resetVotes()
    }

    @Test
    void "clears votes on restaurants"() {
        //given
        when(restaurants.selectWinner()).thenReturn(new Restaurant(UUID.randomUUID() as String, "rhubarbar"))

        //when
        cron.doHousework()

        //then
        verify(restaurants).resetVoteCounts()
    }

    @Test
    void "selects a winner"() {
        //given
        def restaurant = new Restaurant(UUID.randomUUID() as String, "rhubarbar")
        when(restaurants.selectWinner()).thenReturn(restaurant)

        //when
        cron.doHousework()

        //then
        verify(winners).save(Winner.from(restaurant))
    }
}
