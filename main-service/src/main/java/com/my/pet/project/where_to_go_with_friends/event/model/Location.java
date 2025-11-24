package com.my.pet.project.where_to_go_with_friends.event.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.FieldDefaults;

import java.util.Objects;

@Entity
@Table(name = "locations")
@Getter
@Setter
@ToString
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Location {
    //id — уникальный идентификатор места события
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    //широта места проведения события
    @Column(name = "lat")
    Float lat;

    //долгота места проведения события
    @Column(name = "lon")
    Float lon;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location = (Location) o;
        return Objects.equals(lat, location.lat) && Objects.equals(lon, location.lon);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon);
    }
}
