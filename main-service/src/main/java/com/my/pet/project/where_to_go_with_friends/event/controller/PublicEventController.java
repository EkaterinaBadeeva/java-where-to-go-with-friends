package com.my.pet.project.where_to_go_with_friends.event.controller;

import com.my.pet.project.where_to_go_with_friends.event.dto.EventFullDto;
import com.my.pet.project.where_to_go_with_friends.event.dto.EventShortDto;
import com.my.pet.project.where_to_go_with_friends.event.service.EventService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Size;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping(path = "/events")
@RequiredArgsConstructor
public class PublicEventController {
    private final EventService eventService;

    //GET  /events?text={text}&categories={categories}&paid={paid}&rangeStart={rangeStart}
    // &rangeEnd={rangeEnd}&onlyAvailable={onlyAvailable}&sort={sort}&from={from}&size={size}
    // получить информацию обо всех событиях подходящих под переданные условия
    // text - текст для поиска в содержимом аннотации и подробном описании события
    // categories - список идентификаторов категорий в которых будет вестись поиск
    // paid - поиск только платных/бесплатных событий
    // rangeStart - дата и время не раньше которых должно произойти событие
    // rangeEnd - дата и время не позже которых должно произойти событие
    // onlyAvailable - только события у которых не исчерпан лимит запросов на участие
    // sort - Вариант сортировки: по дате события или по количеству просмотров
    // from - количество элементов, которые нужно пропустить для формирования текущего набора,
    // size - количество событий в наборе
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<EventShortDto> getEventsPublic(@RequestParam(required = false) @Size(min = 1, max = 7000) String text,
                                               @RequestParam(required = false) List<Long> categories,
                                               @RequestParam(required = false) Boolean paid,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeStart,
                                               @RequestParam(required = false)
                                               @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime rangeEnd,
                                               @RequestParam(defaultValue = "false") Boolean onlyAvailable,
                                               @RequestParam(required = false) String sort,
                                               @RequestParam(defaultValue = "0") Integer from,
                                               @RequestParam(defaultValue = "10") Integer size,
                                               HttpServletRequest httpServletRequest) {
        return eventService.getEventsPublic(text, categories, paid, rangeStart, rangeEnd, onlyAvailable, sort, from, size, httpServletRequest);
    }

    //GET  /events/{id}
    // получить подробную информацию об опубликованном событии по его идентификатору
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public EventFullDto getEventByIdPublic(@PathVariable Long id,
                                           HttpServletRequest httpServletRequest) {
        return eventService.getEventByIdPublic(id, httpServletRequest);
    }

}