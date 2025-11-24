package com.my.pet.project.where_to_go_with_friends.event.controller;

import com.my.pet.project.where_to_go_with_friends.event.dto.*;
import com.my.pet.project.where_to_go_with_friends.event.service.EventService;
import com.my.pet.project.where_to_go_with_friends.request.dto.ParticipationRequestDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/events")
@RequiredArgsConstructor
public class PrivateEventController {
    private final EventService eventService;

    //POST /users/{userId}/events
    // добавить новое событие
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EventFullDto create(@Valid @RequestBody NewEventDto eventDto,
                               @PathVariable Long userId) {
        return eventService.createEventPrivate(eventDto, userId);
    }

    //GET  /users/{userId}/events?from={from}&size={size}
    // получить события, добавленные текущим пользователем
    // from - количество элементов, которые нужно пропустить для формирования текущего набора,
    // size - количество событий в наборе
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEvents(@RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                         @RequestParam(defaultValue = "10") @Positive Integer size,
                                         @PathVariable Long userId) {
        return eventService.getEventsPrivate(from, size, userId);
    }

    //GET  /users/{userId}/events/{eventId}
    // получить полную информации о событии добавленном текущим пользователем
    @GetMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventById(@PathVariable Long eventId,
                                     @PathVariable Long userId) {
        return eventService.getEventByIdPrivate(eventId, userId);
    }

    //PATCH /users/{userId}/events/{eventId}
    // измененить событие добавленное текущим пользователем
    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEvent(@Valid @RequestBody UpdateEventUserRequest eventDto,
                                    @PathVariable Long eventId,
                                    @PathVariable Long userId) {
        return eventService.updateEventPrivate(eventDto, eventId, userId);
    }

    //GET  /users/{userId}/events/{eventId}/requests
    // получить информацию о запросах на участие в событии текущего пользователя
    @GetMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getRequests(@PathVariable Long eventId,
                                                     @PathVariable Long userId) {
        return eventService.getRequestsPrivate(eventId, userId);
    }

    //PATCH  /users/{userId}/events/{eventId}/requests
    // измененить статус (подтверждена, отменена) заявок на участие в событии текущего пользователя
    @PatchMapping("/{eventId}/requests")
    @ResponseStatus(HttpStatus.OK)
    public EventRequestStatusUpdateResult updateRequestsStatus(@Valid @RequestBody EventRequestStatusUpdateRequest dto,
                                                               @PathVariable Long eventId,
                                                               @PathVariable Long userId) {
        return eventService.updateRequestsStatusPrivate(dto, eventId, userId);
    }
}
