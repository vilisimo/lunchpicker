package org.lunchpicker.domain;

import org.lunchpicker.util.Validations;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Restaurant {

    @Id
    private String id;
    private String name;
    private float votes;
    private int uniqueVotes;

    private Restaurant() { /* For serialization/deserialization */ }

    public Restaurant(String name) {
        this.id = UUID.randomUUID().toString();
        this.name = name;
    }

    public Restaurant(String id, String name) {
        Validations.isUuid(id);

        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public float getVotes() {
        return votes;
    }

    public int getUniqueVotes() {
        return uniqueVotes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Restaurant that = (Restaurant) o;
        return Float.compare(that.votes, votes) == 0 &&
                uniqueVotes == that.uniqueVotes &&
                Objects.equals(id, that.id) &&
                Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, votes, uniqueVotes);
    }
}
