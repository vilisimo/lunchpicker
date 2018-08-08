package org.lunchpicker.domain;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.Objects;

@Entity
public class Winner {

    @Id
    private String id;
    private String name;
    private LocalDate date;

    private Winner() { /* For serialization/deserialization */ }

    public static Winner from(Restaurant restaurant) {
        Winner winner = new Winner();
        winner.id = restaurant.getId();
        winner.name = restaurant.getName();
        winner.date = LocalDate.now();
        return winner;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Winner winner = (Winner) o;
        return Objects.equals(id, winner.id) &&
                Objects.equals(name, winner.name) &&
                Objects.equals(date, winner.date);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, date);
    }
}
