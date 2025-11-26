package com.my.pet.project.where_to_go_with_friends.event.service;

import com.my.pet.project.where_to_go_with_friends.event.dto.*;
import com.my.pet.project.where_to_go_with_friends.event.model.Event;
import com.my.pet.project.where_to_go_with_friends.request.dto.ParticipationRequestDto;
import jakarta.servlet.http.HttpServletRequest;

import java.time.LocalDateTime;
import java.util.List;

public interface EventService {
    EventFullDto createEventPrivate(NewEventDto eventDto, Long userId);

    List<EventShortDto> getEventsPrivate(Integer from, Integer size, Long userId);

    EventFullDto getEventByIdPrivate(Long eventId, Long userId);

    EventFullDto updateEventPrivate(UpdateEventUserRequest eventDto, Long eventId, Long userId);

    List<ParticipationRequestDto> getRequestsPrivate(Long eventId, Long userId);

    EventRequestStatusUpdateResult updateRequestsStatusPrivate(EventRequestStatusUpdateRequest dto,
                                                               Long eventId, Long userId);

    List<EventFullDto> getEventsAdmin(List<Long> users,
                                      List<String> states,
                                      List<Long> categories,
                                      LocalDateTime rangeStart,
                                      LocalDateTime rangeEnd,
                                      Integer from,
                                      Integer size);

    EventFullDto updateEventAdmin(UpdateEventAdminRequest eventDto, Long eventId);


    EventFullDto getEventByIdPublic(Long id, HttpServletRequest httpServletRequest);


    List<EventShortDto> getEventsPublic(String text, List<Long> categories, Boolean paid, LocalDateTime rangeStart, LocalDateTime rangeEnd, Boolean onlyAvailable, String sort, Integer from, Integer size, HttpServletRequest httpServletRequest);

    Event findEventById(Long eventId);

    List<Event> findEventsByIds(List<Long> eventIds);

    List<Event> findEventsByCategoryId(Long catId);

    int calculateRating(int sumLikes, int sumDislikes);
}
