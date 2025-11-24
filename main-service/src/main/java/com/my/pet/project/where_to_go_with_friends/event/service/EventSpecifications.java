package com.my.pet.project.where_to_go_with_friends.event.service;

import com.my.pet.project.where_to_go_with_friends.event.model.Event;
import com.my.pet.project.where_to_go_with_friends.event.model.EventState;
import org.springframework.data.jpa.domain.Specification;

import java.time.LocalDateTime;
import java.util.List;

public class EventSpecifications {

    public static Specification<Event> hasInitiators(List<Long> initiatorIds) {
        return (root, query, cb) ->
                initiatorIds == null || initiatorIds.isEmpty()
                        ? cb.conjunction()
                        : root.get("initiator").get("id").in(initiatorIds);
    }

    public static Specification<Event> hasStates(List<String> states) {
        return (root, query, cb) ->
                states == null || states.isEmpty()
                        ? cb.conjunction()
                        : root.get("state").in(states);
    }

    public static Specification<Event> hasCategories(List<Long> categoryIds) {
        return (root, query, cb) ->
                categoryIds == null || categoryIds.isEmpty()
                        ? cb.conjunction()
                        : root.get("category").get("id").in(categoryIds);
    }

    public static Specification<Event> dateBetween(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        return (root, query, cb) -> {
            if (rangeStart == null && rangeEnd == null) return cb.conjunction();
            if (rangeStart == null) return cb.lessThanOrEqualTo(root.get("eventDate"), rangeEnd);
            if (rangeEnd == null) return cb.greaterThanOrEqualTo(root.get("eventDate"), rangeStart);
            return cb.between(root.get("eventDate"), rangeStart, rangeEnd);
        };
    }

    public static Specification<Event> hasText(String text) {
        return (root, query, cb) -> {
            if (text != null && !text.isBlank()) {
                String pattern = "%" + text.toLowerCase() + "%";
                return cb.or(
                        cb.like(cb.lower(root.get("annotation")), pattern),
                        cb.like(cb.lower(root.get("description")), pattern)
                );
            }
            return cb.conjunction();
        };
    }

    public static Specification<Event> hasPaid(Boolean paid) {
        return (root, query, cb) ->
                paid != null ? cb.equal(root.get("paid"), paid) : cb.conjunction();
    }

    public static Specification<Event> isAvailable(Boolean onlyAvailable) {
        return (root, query, cb) ->
                onlyAvailable != null && onlyAvailable
                        ? cb.greaterThan(root.get("participantLimit"), 0)
                        : cb.conjunction();
    }

    public static Specification<Event> hasState(EventState state) {
        return (root, query, cb) ->
                state != null ? cb.equal(root.get("state"), state) : cb.conjunction();
    }

    public static Specification<Event> dateBetweenOrAfterNow(LocalDateTime start, LocalDateTime end) {
        return (root, query, cb) -> {
            if (start == null && end == null) {

                return cb.greaterThan(root.get("eventDate"), LocalDateTime.now());
            }
            if (start == null) {
                return cb.lessThanOrEqualTo(root.get("eventDate"), end);
            }
            if (end == null) {
                return cb.greaterThanOrEqualTo(root.get("eventDate"), start);
            }
            return cb.between(root.get("eventDate"), start, end);
        };
    }
}
