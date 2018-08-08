package org.lunchpicker.service

import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.lunchpicker.configuration.UserProperties
import org.lunchpicker.domain.User
import org.lunchpicker.persistence.UserRepository
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

import static org.mockito.ArgumentMatchers.any
import static org.mockito.ArgumentMatchers.anyString
import static org.mockito.Mockito.verify
import static org.mockito.Mockito.when

@RunWith(MockitoJUnitRunner)
class UserServiceTest {

    @Mock
    UserRepository repository

    UserService service

    UserProperties props

    @Before
    void setup() {
        props = new UserProperties()
        props.setVotes(10)
        service = new UserService(props, repository)
    }

    @Test
    void "returns an existing user"() {
        //given
        def user = new User("existing", 10)
        when(repository.findById(user.username)).thenReturn(Optional.ofNullable(user))

        //when
        def result = service.getUser(user.username)

        //then
        result == user
    }

    @Test
    void "creates a new user if no user exists"() {
        //given
        when(repository.findById(anyString())).thenReturn(Optional.ofNullable())
        when(repository.save(any(User))).thenReturn(new User("username", props.votes))

        //when
        def result = service.getUser("username")

        //then
        result.username == "username"
        result.votes == props.votes
    }

    @Test
    void "saves a user"() {
        //when
        service.save(new User("username", 10))

        //then
        verify(repository).save(any(User))
    }

    @Test
    void "resets votes"() {
        //when
        service.resetVotes()

        //then
        verify(repository).resetVotes()
    }
}