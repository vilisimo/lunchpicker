package org.lunchpicker.service

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.persistence.RestaurantRepository
import org.lunchpicker.persistence.UserRepository
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import static org.mockito.Mockito.verify

@RunWith(MockitoJUnitRunner)
class CronTest {

    @Mock
    UserRepository users

    @Mock
    RestaurantRepository restaurants

    Cron cron

    @Before
    void setup() {
        cron = new Cron(users, restaurants)
    }

    @Test
    void "clears user votes"() {
        //when
        cron.doHousework()

        //then
        verify(users).resetVotes()
    }

    @Test
    void "clears votes on restaurants"() {
        //when
        cron.doHousework()

        //then
        verify(restaurants).resetVoteCounts()
    }
}
