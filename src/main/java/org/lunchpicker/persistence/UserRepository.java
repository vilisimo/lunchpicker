package org.lunchpicker.persistence;

import org.lunchpicker.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE User u SET u.votes = 10")
    @Transactional
    void resetVotes();
}
