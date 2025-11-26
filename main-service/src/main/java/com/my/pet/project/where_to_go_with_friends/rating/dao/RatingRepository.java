package com.my.pet.project.where_to_go_with_friends.rating.dao;

import com.my.pet.project.where_to_go_with_friends.rating.model.Rating;
import com.my.pet.project.where_to_go_with_friends.rating.model.RatingState;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Rating, Long> {

    Boolean existsByUserIdAndEventId(Long userId, Long eventId);

    Optional<Rating> findByUserIdAndEventId(Long userId, Long eventId);

    List<Rating> findAllByEventIdAndState(Long eventId, RatingState state);

    List<Rating> findAllByEventId(Long eventId);

    List<List<Rating>> findAllByEventIdIn(List<Long> eventIds);
}
