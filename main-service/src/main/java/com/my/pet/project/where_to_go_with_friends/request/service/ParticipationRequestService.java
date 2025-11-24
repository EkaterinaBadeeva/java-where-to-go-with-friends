package com.my.pet.project.where_to_go_with_friends.request.service;

import com.my.pet.project.where_to_go_with_friends.request.dto.ParticipationRequestDto;

import java.util.List;

public interface ParticipationRequestService {
    ParticipationRequestDto create(Long eventId, Long userId);

    ParticipationRequestDto cancelParticipationRequest(Long userId, Long requestId);

    List<ParticipationRequestDto> getParticipationRequest(Long userId);
}
