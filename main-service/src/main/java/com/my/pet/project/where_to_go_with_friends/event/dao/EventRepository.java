package com.my.pet.project.where_to_go_with_friends.event.dao;

import com.my.pet.project.where_to_go_with_friends.event.model.Event;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EventRepository extends JpaRepository<Event, Long>, JpaSpecificationExecutor<Event> {
    Page<Event> findAllByInitiatorId(Long initiatorId, Pageable pageable);

    Optional<Event> findByIdAndInitiatorId(Long eventId, Long initiatorId);

    List<Event> findAllByCategoryId(Long catId);

    List<Event> findAllByInitiatorId(Long initiatorId);

    List<Event> findAllByInitiatorIdIn(Set<Long> initiatorIds);
}
