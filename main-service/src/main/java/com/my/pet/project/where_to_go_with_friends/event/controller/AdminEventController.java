package com.my.pet.project.where_to_go_with_friends.event.controller;

import com.my.pet.project.where_to_go_with_friends.event.dto.EventFullDto;
import com.my.pet.project.where_to_go_with_friends.event.dto.UpdateEventAdminRequest;
import com.my.pet.project.where_to_go_with_friends.event.service.EventService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/admin/events")
@RequiredArgsConstructor
public class AdminEventController {
    private final EventService eventService;

    //GET  /admin/events?users={users}&states={states}&categories={categories}
    //     &rangeStart={rangeStart}&rangeEnd={rangeEnd}&from={from}&size={size}
    // получить информацию обо всех событиях подходящих под переданные условия
    // users - список id пользователей, чьи события нужно найти
    // states - список состояний в которых находятся искомые события
    // categories - список id категорий в которых будет вестись поиск
    // rangeStart - дата и время не раньше которых должно произойти событие
    // rangeEnd - дата и время не позже которых должно произойти событие
    // from - количество элементов, которые нужно пропустить для формирования текущего набора,
    // size - количество событий в наборе
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventFullDto> getEventsAdmin(@RequestParam(required = false) List<Long> users,
                                             @RequestParam(required = false) List<String> states,
                                             @RequestParam(required = false) List<Long> categories,
                                             @RequestParam(required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                             LocalDateTime rangeStart,
                                             @RequestParam(required = false)
                                             @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                                             LocalDateTime rangeEnd,
                                             @RequestParam(defaultValue = "0") @PositiveOrZero Integer from,
                                             @RequestParam(defaultValue = "10") @Positive Integer size) {
        return eventService.getEventsAdmin(users, states, categories, rangeStart, rangeEnd, from, size);
    }

    //PATCH  /admin/events/{eventId}
    // редактировать данные события и его статуса (отклонение/публикация) администратором.
    @PatchMapping("/{eventId}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto updateEventAdmin(@Valid @RequestBody UpdateEventAdminRequest eventDto,
                                         @PathVariable Long eventId) {
        return eventService.updateEventAdmin(eventDto, eventId);
    }
}