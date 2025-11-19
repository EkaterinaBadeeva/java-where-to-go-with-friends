package com.my.pet.project.where_to_go_with_friends.event.dao;

import com.my.pet.project.where_to_go_with_friends.event.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LocationRepository extends JpaRepository<Location, Long> {
    Optional<Location> findByLatAndLon(Float lat, Float lon);
}
