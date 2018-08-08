package org.lunchpicker.domain;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
public class User {

    @Id
    private String username;

    private int votes;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "username")
    private Set<Vote> castVotes = new HashSet<>();

    private User() { /* For serialization/deserialization */ }

    public User(String username, int votes) {
        this.username = username;
        this.votes = votes;
    }

    public void addVote(Vote vote) {
        this.castVotes.add(vote);
        this.votes -= 1;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public Set<Vote> getCastVotes() {
        return castVotes;
    }

    public void setCastVotes(Set<Vote> castVotes) {
        this.castVotes = castVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return votes == user.votes &&
                Objects.equals(username, user.username) &&
                Objects.equals(castVotes, user.castVotes);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username, votes, castVotes);
    }
}
