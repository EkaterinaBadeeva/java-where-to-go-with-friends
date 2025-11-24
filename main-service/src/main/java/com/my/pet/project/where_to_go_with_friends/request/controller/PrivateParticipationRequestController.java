package com.my.pet.project.where_to_go_with_friends.request.controller;

import com.my.pet.project.where_to_go_with_friends.request.dto.ParticipationRequestDto;
import com.my.pet.project.where_to_go_with_friends.request.service.ParticipationRequestService;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/users/{userId}/requests")
@RequiredArgsConstructor
public class PrivateParticipationRequestController {
    private final ParticipationRequestService participationRequestService;

    //POST /users/{userId}/requests?eventId={eventId}
    // добавить запрос от текущего пользователя на участие в событии
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ParticipationRequestDto create(@RequestParam @Positive Long eventId,
                                          @PathVariable @Positive Long userId) {
        return participationRequestService.create(eventId, userId);
    }

    //GET  /users/{userId}/requests
    // получить информацию о заявках текущего пользователя на участие в чужих событиях
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ParticipationRequestDto> getParticipationRequest(@PathVariable Long userId) {
        return participationRequestService.getParticipationRequest(userId);
    }

    //PATCH /users/{userId}/requests/{requestId}/cancel
    //отменить свой запрос на участие в событии
    @PatchMapping("/{requestId}/cancel")
    @ResponseStatus(HttpStatus.OK)
    public ParticipationRequestDto cancelParticipationRequest(@PathVariable Long userId,
                                                              @PathVariable Long requestId) {
        return participationRequestService.cancelParticipationRequest(userId, requestId);
    }
}
