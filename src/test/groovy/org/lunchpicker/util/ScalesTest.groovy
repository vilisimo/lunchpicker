package org.lunchpicker.util

import org.junit.Test
import org.lunchpicker.domain.User
import org.lunchpicker.domain.Vote

class ScalesTest {

    @Test
    void "recognizes first vote"() {
        //given
        def user = new User("username", 10)
        user.castVotes = [new Vote("non-related")] as Set

        //when
        def weight = Scales.weighUserVote(user, "restaurant")

        //then
        weight == 1f
    }

    @Test
    void "recognizes second vote"() {
        //given
        def user = new User("username", 10)
        user.castVotes = [new Vote("restaurant")] as Set

        //when
        def weight = Scales.weighUserVote(user, "restaurant")

        //then
        weight == 0.5f
    }

    @Test
    void "recognizes third vote"() {
        //given
        def user = new User("username", 10)
        user.castVotes = [new Vote("restaurant")] * 2 as Set

        //when
        def weight = Scales.weighUserVote(user, "restaurant")

        //then
        weight == 0.25f
    }

    @Test
    void "recognizes >3rd votes"() {
        //given
        def user = new User("username", 10)
        user.castVotes = [new Vote("restaurant")] * 5  as Set

        //when
        def weight = Scales.weighUserVote(user, "restaurant")

        //then
        weight == 0.25f
    }
}
