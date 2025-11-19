package com.my.pet.project.where_to_go_with_friends.event.service;

import com.my.pet.project.HitDto;
import com.my.pet.project.StatClient;
import com.my.pet.project.ViewStatsDto;
import com.my.pet.project.where_to_go_with_friends.category.dao.CategoryRepository;
import com.my.pet.project.where_to_go_with_friends.category.model.Category;
import com.my.pet.project.where_to_go_with_friends.event.dao.EventRepository;
import com.my.pet.project.where_to_go_with_friends.event.dao.LocationRepository;
import com.my.pet.project.where_to_go_with_friends.event.dto.*;
import com.my.pet.project.where_to_go_with_friends.event.mapper.EventMapper;
import com.my.pet.project.where_to_go_with_friends.event.model.Event;
import com.my.pet.project.where_to_go_with_friends.event.model.EventState;
import com.my.pet.project.where_to_go_with_friends.event.model.Location;
import com.my.pet.project.where_to_go_with_friends.event.model.StateAction;
import com.my.pet.project.where_to_go_with_friends.exceptions.ConflictException;
import com.my.pet.project.where_to_go_with_friends.exceptions.NotFoundException;
import com.my.pet.project.where_to_go_with_friends.exceptions.ValidationException;
import com.my.pet.project.where_to_go_with_friends.request.dao.ParticipationRequestRepository;
import com.my.pet.project.where_to_go_with_friends.request.dto.ParticipationRequestDto;
import com.my.pet.project.where_to_go_with_friends.request.mapper.ParticipationRequestMapper;
import com.my.pet.project.where_to_go_with_friends.request.model.ParticipationRequest;
import com.my.pet.project.where_to_go_with_friends.request.model.ParticipationRequestStatus;
import com.my.pet.project.where_to_go_with_friends.user.model.User;
import com.my.pet.project.where_to_go_with_friends.user.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

import static com.my.pet.project.where_to_go_with_friends.event.model.EventState.*;
import static com.my.pet.project.where_to_go_with_friends.event.model.StateAction.*;
import static org.springframework.data.jpa.domain.Specification.where;


@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class EventServiceImpl implements EventService {
    private final EventRepository eventRepository;
    private final LocationRepository locationRepository;
    private final ParticipationRequestRepository requestRepository;
    private final UserService userService;
    private final CategoryRepository categoryRepository;
    private final StatClient statClient;

    @Override
    @Transactional
    public EventFullDto createEventPrivate(NewEventDto eventDto, Long userId) {
        log.info("Добавление нового события.");
        checkId(userId);
        User user = findAndCheckUser(userId);

        Long catId = eventDto.getCategory();
        Category category = findAndCheckCategory(catId);

        Event event = EventMapper.mapToEvent(eventDto);
        checkConditions(event);

        LocationDto locationDto = eventDto.getLocation();
        Optional<Location> locationOpt = locationRepository.findByLatAndLon(locationDto.getLat(), locationDto.getLon());
        Location location = locationOpt.orElseGet(() -> createLocation(locationDto));

        event.setLocation(location);
        event.setCategory(category);
        event.setInitiator(user);
        event.setCreatedOn(LocalDateTime.now());
        event.setState(PENDING);
        event = eventRepository.save(event);

        return EventMapper.mapToEventFullDto(event);
    }

    @Override
    public List<EventShortDto> getEventsPrivate(Integer from, Integer size, Long userId) {
        log.info("Получение событий, добавленных текущим пользователем.");

        checkId(userId);
        findAndCheckUser(userId);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        Page<Event> eventPage = eventRepository.findAllByInitiatorId(userId, pageable);

        return eventPage.getContent()
                .stream()
                .map(EventMapper::mapToEventShortDto)
                .toList();
    }

    @Override
    public EventFullDto getEventByIdPrivate(Long eventId, Long userId) {
        log.info("Получение полной информации о событии добавленном текущим пользователем.");

        checkId(userId);
        checkId(eventId);
        findAndCheckUser(userId);

        Event event = findAndCheckEventByIdAndInitiatorId(eventId, userId);

        return EventMapper.mapToEventFullDto(event);
    }

    @Override
    @Transactional
    public EventFullDto updateEventPrivate(UpdateEventUserRequest eventDto, Long eventId, Long userId) {
        log.info("Изменение события добавленного текущим пользователем.");
        checkId(userId);
        checkId(eventId);
        findAndCheckUser(userId);
        Event oldEvent = findAndCheckEventByIdAndInitiatorId(eventId, userId);

        if (oldEvent.getState().equals(PUBLISHED)) {
            log.warn("Событие уже опубликовано. Событие с Id = {} нельзя именить", eventId);
            throw new ConflictException("Событие уже опубликовано. Событие с Id = " + eventId + " нельзя именить");
        }

        if (eventDto.getEventDate() != null && eventDto.getEventDate().isBefore(LocalDateTime.now())) {
            log.warn("Дата и время на которые намечено событие не может быть в прошлом.");
            throw new ValidationException("Дата и время на которые намечено событие не может быть в прошлом.");
        }

        if (eventDto.getEventDate() != null && eventDto.getEventDate().isBefore(LocalDateTime.now().plusHours(2))) {
            log.warn("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента");
            throw new ConflictException("Дата и время на которые намечено событие не может быть раньше, " +
                    "чем через два часа от текущего момента");
        }

        String newAnnotation = eventDto.getAnnotation();
        if (((newAnnotation != null)) && (newAnnotation.length() > 20 && newAnnotation.length() < 2000)) {
            oldEvent.setAnnotation(newAnnotation);
        }

        Long categoryId = eventDto.getCategory();
        if (categoryId != null) {
            Category category = findAndCheckCategory(categoryId);
            oldEvent.setCategory(category);
        }

        String newDescription = eventDto.getDescription();
        if (((newDescription != null)) && (newDescription.length() > 20 && newDescription.length() < 7000)) {
            oldEvent.setDescription(newDescription);
        }

        if (eventDto.getEventDate() != null) {
            oldEvent.setEventDate(eventDto.getEventDate());
        }

        LocationDto locationDto = eventDto.getLocation();
        if (locationDto != null) {
            Optional<Location> locationOpt = locationRepository.findByLatAndLon(locationDto.getLat(), locationDto.getLon());
            Location location = locationOpt.orElseGet(() -> createLocation(locationDto));
            oldEvent.setLocation(location);
        }

        if (eventDto.getPaid() != null) {
            oldEvent.setPaid(eventDto.getPaid());
        }

        Integer participantLimit = eventDto.getParticipantLimit();
        if (participantLimit != null && participantLimit >= 0) {
            oldEvent.setParticipantLimit(eventDto.getParticipantLimit());
        }

        Boolean requestModeration = eventDto.getRequestModeration();
        if (requestModeration != null) {
            oldEvent.setRequestModeration(requestModeration);
        }

        StateAction stateAction = eventDto.getStateAction();
        if (stateAction != null) {

            switch (stateAction) {
                case SEND_TO_REVIEW -> oldEvent.setState(PENDING);
                case CANCEL_REVIEW -> oldEvent.setState(CANCELED);
            }
        }

        String title = eventDto.getTitle();
        if (((title != null)) && (title.length() > 3 && title.length() < 120)) {
            oldEvent.setTitle(title);
        }
        return EventMapper.mapToEventFullDto(oldEvent);
    }

    @Override
    public List<ParticipationRequestDto> getRequestsPrivate(Long eventId, Long userId) {
        log.info("Получение информации о запросах на участие в событии текущего пользователя.");

        checkId(userId);
        checkId(eventId);
        findAndCheckUser(userId);
        findAndCheckEventByIdAndInitiatorId(eventId, userId);

        List<ParticipationRequest> requests = requestRepository.findAllByEventId(eventId);

        return requests.stream()
                .map(ParticipationRequestMapper::mapToParticipationRequestDto)
                .toList();
    }

    @Override
    @Transactional
    public EventRequestStatusUpdateResult updateRequestsStatusPrivate(EventRequestStatusUpdateRequest dto, Long eventId, Long userId) {
        log.info("Изменение статуса (подтверждена, отменена) заявок на участие в событии текущего пользователя.");

        checkId(userId);
        checkId(eventId);
        User user = findAndCheckUser(userId);
        Event event = findAndCheckEventByIdAndInitiatorId(eventId, userId);
        List<Long> requestIds = dto.getRequestIds();
        ParticipationRequestStatus status = dto.getStatus();
        List<ParticipationRequest> requests = requestRepository.findAllByIdIn(requestIds);

        List<ParticipationRequest> confirmedRequests = requestRepository.findAllByEventIdAndStatus(eventId, ParticipationRequestStatus.CONFIRMED);
        event.setConfirmedRequests(confirmedRequests.size());

        List<ParticipationRequest> rejectedRequests = requestRepository.findAllByEventIdAndStatus(eventId, ParticipationRequestStatus.REJECTED);

        for (ParticipationRequest request : requests) {
            if (request.getStatus() != ParticipationRequestStatus.PENDING) {
                throw new ConflictException("Статус можно изменить только у заявок, находящихся в состоянии ожидания");
            }

            if (event.getConfirmedRequests() >= event.getParticipantLimit()) {
                throw new ConflictException("Достигнут лимит по заявкам на данное событие");
            }
            if (status == ParticipationRequestStatus.CONFIRMED) {
                request.setStatus(ParticipationRequestStatus.CONFIRMED);
                confirmedRequests.add(request);
                event.setConfirmedRequests(event.getConfirmedRequests() + 1);
            } else {
                request.setStatus(ParticipationRequestStatus.REJECTED);
                rejectedRequests.add(request);
            }
        }

        return EventRequestStatusUpdateResult.builder()
                .confirmedRequests(confirmedRequests.stream().map(ParticipationRequestMapper::mapToParticipationRequestDto).toList())
                .rejectedRequests(rejectedRequests.stream().map(ParticipationRequestMapper::mapToParticipationRequestDto).toList())
                .build();
    }

    @Override
    @Transactional
    public List<EventFullDto> getEventsAdmin(List<Long> users,
                                             List<String> states,
                                             List<Long> categories,
                                             LocalDateTime rangeStart,
                                             LocalDateTime rangeEnd,
                                             Integer from, Integer size) {
        log.info("Получение информации администратором обо всех событиях подходящих под переданные условия");

        checkStartAndEnd(rangeStart, rangeEnd);

        int page = from / size;
        Pageable pageable = PageRequest.of(page, size);

        Page<Event> events = eventRepository.findAll(
                where(EventSpecifications.hasInitiators(users))
                        .and(EventSpecifications.hasStates(states))
                        .and(EventSpecifications.hasCategories(categories))
                        .and(EventSpecifications.dateBetween(rangeStart, rangeEnd)),
                pageable
        );

        List<Long> eventIds = events.getContent().stream()
                .map(Event::getId)
                .toList();

        Map<Long, Long> confirmedRequestsCount = requestRepository
                .countByEventIdInAndStatus(eventIds, ParticipationRequestStatus.CONFIRMED)
                .stream()
                .collect(Collectors.toMap(
                        tuple -> ((Number) tuple[0]).longValue(),
                        tuple -> ((Number) tuple[1]).longValue()
                ));

        List<String> uris = eventIds.stream()
                .map(id -> "/events/" + id)
                .toList();
        List<ViewStatsDto> stats = statClient.getStats(
                events.getContent().stream()
                        .map(Event::getPublishedOn)
                        .filter(Objects::nonNull)
                        .min(LocalDateTime::compareTo)
                        .orElse(LocalDateTime.now().plusHours(1)),
                LocalDateTime.now(),
                uris,
                true);

        Map<Long, Long> viewsStats = new HashMap<>();

        for (ViewStatsDto stat : stats) {
            viewsStats.put(Long.parseLong(stat.getUri().substring("/events/".length())), stat.getHits());
        }

        return events.getContent()
                .stream()
                .map(event -> {
                    EventFullDto dto = EventMapper.mapToEventFullDto(event);
                    dto.setConfirmedRequests(Math.toIntExact(confirmedRequestsCount.getOrDefault(event.getId(), 0L)));
                    dto.setViews(viewsStats.getOrDefault(event.getId(), 0L));
                    return dto;
                })
                .toList();
    }

    @Override
    @Transactional
    public EventFullDto updateEventAdmin(UpdateEventAdminRequest eventDto, Long eventId) {
        log.info("Редактирование данных события и его статуса (отклонение/публикация) администратором.");
        checkId(eventId);
        Event oldEvent = eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));

        if (oldEvent.getState().equals(PUBLISHED)) {
            log.warn("Событие уже опубликовано. Событие с Id = {} нельзя именить", eventId);
            throw new ConflictException("Событие уже опубликовано. Событие с Id = " + eventId + " нельзя изменить");
        }

        if (oldEvent.getEventDate() != null && oldEvent.getPublishedOn() != null && oldEvent.getEventDate().isAfter(oldEvent.getPublishedOn().minusHours(1))) {
            log.warn("Дата начала изменяемого события должна быть не ранее чем за час от даты публикации");
            throw new ConflictException("Дата начала изменяемого события должна быть не ранее чем за час от даты публикации");
        }

        String newAnnotation = eventDto.getAnnotation();
        if (((newAnnotation != null)) && (newAnnotation.length() > 20 && newAnnotation.length() < 2000)) {
            oldEvent.setAnnotation(newAnnotation);
        }

        Long categoryId = eventDto.getCategory();
        if (categoryId != null) {
            Category category = findAndCheckCategory(categoryId);
            oldEvent.setCategory(category);
        }

        String newDescription = eventDto.getDescription();
        if (((newDescription != null)) && (newDescription.length() > 20 && newDescription.length() < 7000)) {
            oldEvent.setDescription(newDescription);
        }

        if (eventDto.getEventDate() != null) {
            if (eventDto.getEventDate().isBefore(LocalDateTime.now())) {
                throw new ValidationException("Дата должна быть позже текущией даты");
            }
            oldEvent.setEventDate(eventDto.getEventDate());
        }

        LocationDto locationDto = eventDto.getLocation();
        if (locationDto != null) {
            Optional<Location> locationOpt = locationRepository.findByLatAndLon(locationDto.getLat(), locationDto.getLon());
            Location location = locationOpt.orElseGet(() -> createLocation(locationDto));
            oldEvent.setLocation(location);
        }

        if (eventDto.getPaid() != null) {
            oldEvent.setPaid(eventDto.getPaid());
        }

        Integer participantLimit = eventDto.getParticipantLimit();
        if (participantLimit != null && participantLimit >= 0) {
            oldEvent.setParticipantLimit(eventDto.getParticipantLimit());
        }

        Boolean requestModeration = eventDto.getRequestModeration();
        if (requestModeration != null) {
            oldEvent.setRequestModeration(requestModeration);
        }

        StateAction stateAction = eventDto.getStateAction();
        if (stateAction != null) {
            if (stateAction == PUBLISH_EVENT) {
                if (oldEvent.getState() == CANCELED) {
                    throw new ConflictException("Публикация отмененного события");
                }
                oldEvent.setState(PUBLISHED);
                oldEvent.setPublishedOn(LocalDateTime.now());
            }
            if (stateAction == REJECT_EVENT) {
                oldEvent.setState(CANCELED);
            }
        }

        String title = eventDto.getTitle();
        if (((title != null)) && (title.length() > 3 && title.length() < 120)) {
            oldEvent.setTitle(title);
        }

        return EventMapper.mapToEventFullDto(oldEvent);
    }

    @Override
    @Transactional
    public EventFullDto getEventByIdPublic(Long id, HttpServletRequest httpServletRequest) {
        log.info("Получение подробной информации об опубликованном событии по его идентификатору.");
        Event event = findEventById(id);

        if (event.getState() != PUBLISHED) {
            throw new NotFoundException("Событие " + id + " не опубликовано");
        }

        saveHit(httpServletRequest);

        List<ViewStatsDto> stats = statClient.getStats(event.getPublishedOn().minusSeconds(1L),
                LocalDateTime.now(), List.of("/events/" + id), true);

        Long views = 0L;
        if (stats != null && !stats.isEmpty()) {
            views = stats.getFirst().getHits();
        }
        event.setViews(views);

        return EventMapper.mapToEventFullDto(event);
    }

    @Override
    @Transactional
    public List<EventShortDto> getEventsPublic(String text,
                                               List<Long> categories,
                                               Boolean paid,
                                               LocalDateTime rangeStart,
                                               LocalDateTime rangeEnd,
                                               Boolean onlyAvailable,
                                               String sort, Integer from,
                                               Integer size,
                                               HttpServletRequest httpServletRequest) {
        checkStartAndEnd(rangeStart, rangeEnd);

        int page = from / size;
        Pageable pageable;
        switch (sort) {
            case "EVENT_DATE" -> pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "eventDate"));
            case "VIEWS" -> pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "views"));
            case null -> pageable = PageRequest.of(page, size, Sort.unsorted());
            default -> {
                throw new ValidationException("Указан неизвестный тип сортировки");
            }
        }

        saveHit(httpServletRequest);

        Page<Event> events = eventRepository.findAll(
                where(EventSpecifications.hasText(text))
                        .and(EventSpecifications.hasCategories(categories))
                        .and(EventSpecifications.hasPaid(paid))
                        .and(EventSpecifications.dateBetweenOrAfterNow(rangeStart, rangeEnd))
                        .and(EventSpecifications.isAvailable(onlyAvailable))
                        .and(EventSpecifications.hasState(EventState.PUBLISHED)),
                pageable
        );

        List<Long> eventIds = events.getContent().stream()
                .map(Event::getId)
                .toList();

        List<String> uris = eventIds.stream()
                .map(id -> "/events/" + id)
                .toList();

        Map<Long, Long> viewsStats = new HashMap<>();
        for (ViewStatsDto stat : statClient.getStats(
                events.getContent().stream()
                        .map(Event::getPublishedOn)
                        .filter(Objects::nonNull)
                        .min(LocalDateTime::compareTo)
                        .orElse(LocalDateTime.now().plusHours(1)),
                LocalDateTime.now(),
                uris,
                true)) {
            viewsStats.put(Long.parseLong(stat.getUri().substring("/events/".length())), stat.getHits());
        }

        return events.getContent()
                .stream()
                .map(event -> {
                    EventShortDto dto = EventMapper.mapToEventShortDto(event);
                    dto.setViews(viewsStats.getOrDefault(event.getId(), 0L));
                    return dto;
                })
                .toList();
    }

    @Override
    public Event findEventById(Long eventId) {
        log.info("Получение полной информации о событии по его id.");

        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));
    }

    @Override
    public List<Event> findEventsByIds(List<Long> eventIds) {
        log.info("Получение краткой информации о событиях по их id.");

        return eventRepository.findAllById(eventIds);
    }

    @Override
    public List<Event> findEventsByCategoryId(Long catId) {
        log.info("Получение списка событий по категории");

        return eventRepository.findAllByCategoryId(catId);
    }

    private void checkId(Long id) {
        if (id == null) {
            log.warn("Id должен быть указан.");
            throw new ValidationException("Id должен быть указан");
        }
    }

    private void checkConditions(Event event) {
        if (event.getAnnotation().isEmpty()) {
            log.warn("Задано пустое краткое описание события.");
            throw new ValidationException("Задано пустое краткое описание события.");
        }

        if (event.getDescription().isEmpty()) {
            log.warn("Задано пустое полное описание события.");
            throw new ValidationException("Задано пустое полное описание события.");
        }

        if (event.getTitle().isEmpty()) {
            log.warn("Задан пустой заголовок события.");
            throw new ValidationException("Задан пустой заголовок события.");
        }
    }

    private void checkStartAndEnd(LocalDateTime rangeStart, LocalDateTime rangeEnd) {
        if (rangeStart != null && rangeEnd != null && rangeStart.isAfter(rangeEnd)) {
            throw new ValidationException("Дата старта должна быть раньше чем даты окончания");
        }
    }

    private Category findAndCheckCategory(Long catId) {

        return categoryRepository.findById(catId)
                .orElseThrow(() -> new NotFoundException("Категория с Id = " + catId + " не найдена"));
    }

    private User findAndCheckUser(Long userId) {

        return userService.findUserById(userId);
    }

    private Event findAndCheckEventByIdAndInitiatorId(Long eventId, Long userId) {
        log.info("Получение информации по Id о событии добавленном текущим пользователем.");

        return eventRepository.findByIdAndInitiatorId(eventId, userId)
                .orElseThrow(() -> new NotFoundException("Событие с id = " + eventId + " не найдено"));
    }

    private Location createLocation(LocationDto locationDto) {
        log.info("Добавление новой локации.");

        Location location = new Location();
        location.setLat(locationDto.getLat());
        location.setLon(locationDto.getLon());
        locationRepository.save(location);

        return location;
    }

    private void saveHit(HttpServletRequest httpServletRequest) {
        log.info("Сохранение информации о том, что на uri конкретного сервиса был отправлен запрос пользователем.");

        HitDto hitRequest = new HitDto(
                "main-server",
                httpServletRequest.getRequestURI(),
                httpServletRequest.getRemoteAddr(),
                LocalDateTime.now()
        );
        statClient.saveHit(hitRequest);
    }
}