package com.my.pet.project.where_to_go_with_friends.request.service;

import com.my.pet.project.where_to_go_with_friends.event.model.Event;
import com.my.pet.project.where_to_go_with_friends.event.service.EventService;
import com.my.pet.project.where_to_go_with_friends.exceptions.ConflictException;
import com.my.pet.project.where_to_go_with_friends.exceptions.NotFoundException;
import com.my.pet.project.where_to_go_with_friends.exceptions.ValidationException;
import com.my.pet.project.where_to_go_with_friends.request.dao.ParticipationRequestRepository;
import com.my.pet.project.where_to_go_with_friends.request.dto.ParticipationRequestDto;
import com.my.pet.project.where_to_go_with_friends.request.mapper.ParticipationRequestMapper;
import com.my.pet.project.where_to_go_with_friends.request.model.ParticipationRequest;
import com.my.pet.project.where_to_go_with_friends.user.model.User;
import com.my.pet.project.where_to_go_with_friends.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.my.pet.project.where_to_go_with_friends.event.model.EventState.PUBLISHED;
import static com.my.pet.project.where_to_go_with_friends.request.model.ParticipationRequestStatus.*;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipationRequestServiceImpl implements ParticipationRequestService {
    private final ParticipationRequestRepository participationRequestRepository;
    private final UserService userService;
    private final EventService eventService;

    @Override
    @Transactional
    public ParticipationRequestDto create(Long eventId, Long userId) {
        log.info("Добавление запроса от текущего пользователя на участие в событии.");
        checkId(eventId);
        checkId(userId);

        checkExistRequest(userId, eventId);
        User user = findAndCheckUser(userId);
        Event event = findAndCheckEvent(eventId, userId);

        ParticipationRequest request = new ParticipationRequest();
        request.setCreated(LocalDateTime.now());
        request.setEvent(event);
        request.setRequester(user);

        if (event.getRequestModeration().equals(false) || event.getParticipantLimit() == 0) {
            request.setStatus(CONFIRMED);
        } else {
            request.setStatus(PENDING);
        }

        request = participationRequestRepository.save(request);
        return ParticipationRequestMapper.mapToParticipationRequestDto(request);
    }

    @Override
    @Transactional
    public ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId) {
        log.info("Отмена своего запроса на участие в событии.");
        findAndCheckUser(userId);

        ParticipationRequest request = participationRequestRepository.findById(requestId).orElseThrow(() ->
                new NotFoundException("Запрос с Id = " + requestId + " не найден"));

        if (!request.getRequester().getId().equals(userId)) {
            log.warn("Невозможно отменить запрос от другого пользователя на участие в событии c id = {} уже существует", request.getEvent().getId());
            throw new ConflictException("Невозможно отменить запрос от другого пользователя на участие в событии c id = "
                    + request.getEvent().getId());
        }

        request.setStatus(CANCELED);
        return ParticipationRequestMapper.mapToParticipationRequestDto(request);
    }

    @Override
    public List<ParticipationRequestDto> getParticipationRequest(Long userId) {
        log.info("Получение информации о заявках текущего пользователя на участие в чужих событиях.");
        checkId(userId);
        findAndCheckUser(userId);
        List<ParticipationRequest> requests = participationRequestRepository.findAllByRequesterId(userId);
        return requests.stream().map(ParticipationRequestMapper::mapToParticipationRequestDto).toList();
    }

    @Override
    public ParticipationRequest findParticipationRequestByUserIdAndEventId(Long userId,Long eventId) {
        log.info("Получение информации о заявке текущего пользователя на участие в конкректном событии.");
        return participationRequestRepository.findByRequesterIdAndEventId(userId, eventId);
    }

    private void checkId(Long id) {
        if (id == null) {
            log.warn("Id должен быть указан.");
            throw new ValidationException("Id должен быть указан");
        }
    }

    private void checkExistRequest(Long userId, Long eventId) {

        if (participationRequestRepository.existsByRequesterIdAndEventId(userId, eventId)) {
            log.warn("Запрос от пользователя с id = {} на участие в событии c id = {} уже существует", userId, eventId);
            throw new ConflictException("Запрос от пользователя с id = " + userId + " на участие в событии c id = "
                    + eventId + " уже существует");
        }
    }

    private User findAndCheckUser(Long userId) {
        return userService.findUserById(userId);
    }

    private Event findAndCheckEvent(Long eventId, Long userId) {

        Event event = eventService.findEventById(eventId);

        if ((event.getInitiator().getId()).equals(userId)) {
            log.warn("Инициатор события не может добавить запрос на участие в своём событии");
            throw new ConflictException("Инициатор события не может добавить запрос на участие в своём событии");
        }

        if (!event.getState().equals(PUBLISHED)) {
            log.warn("Нельзя участвовать в неопубликованном событии.");
            throw new ConflictException("Нельзя участвовать в неопубликованном событии");
        }

        List<ParticipationRequest> requestsConfirmed = participationRequestRepository.findAllByEventIdAndStatus(eventId, CONFIRMED);
        long amountRequests = requestsConfirmed.size();

        if (event.getParticipantLimit() > 0 && amountRequests >= event.getParticipantLimit()) {
            log.warn("У события достигнут лимит запросов на участие.");
            throw new ConflictException("У события достигнут лимит запросов на участие");
        }

        return event;
    }
}