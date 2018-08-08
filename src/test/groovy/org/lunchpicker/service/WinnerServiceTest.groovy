package org.lunchpicker.service

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.domain.Restaurant
import org.lunchpicker.domain.Winner
import org.lunchpicker.persistence.WinnerRepository
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import static org.mockito.ArgumentMatchers.any
import static org.mockito.Mockito.verify

@RunWith(MockitoJUnitRunner.class)
class WinnerServiceTest {

    @Mock
    WinnerRepository winners

    WinnerService service

    @Before
    void setup() {
        service = new WinnerService(winners)
    }

    @Test
    void "saves a winner restaurant"() {
        //when
        service.save(Winner.from(new Restaurant(UUID.randomUUID() as String, "name")))

        //then
        verify(winners).save(any(Winner))
    }
}