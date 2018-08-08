package org.lunchpicker.persistence;

import org.lunchpicker.domain.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;

@Repository
public interface RestaurantRepository extends JpaRepository<Restaurant, String> {

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Restaurant r " +
            "SET r.votes = r.votes + :weight, r.uniqueVotes = r.uniqueVotes + :uniqueVotes " +
            "WHERE r.id = :id")
    @Transactional
    int vote(@Param("id") String uuid, @Param("weight") float weight, @Param("uniqueVotes") int uniqueVotes);

    @Modifying(clearAutomatically = true)
    @Query("UPDATE Restaurant r SET r.votes = 0, r.uniqueVotes = 0")
    @Transactional
    void resetVoteCounts();

    /**
     * Uses Spring JPA derived queries. Looks awful but is quite sufficient
     * for proof of concept work.
     */
    Restaurant findFirstByOrderByVotesDescUniqueVotesDesc();
}
