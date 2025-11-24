package com.my.pet.project.where_to_go_with_friends.request.mapper;

import com.my.pet.project.where_to_go_with_friends.request.dto.ParticipationRequestDto;
import com.my.pet.project.where_to_go_with_friends.request.model.ParticipationRequest;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ParticipationRequestMapper {

    public static ParticipationRequestDto mapToParticipationRequestDto(ParticipationRequest request) {
        return ParticipationRequestDto.builder()
                .created(request.getCreated())
                .event(request.getEvent().getId())
                .id(request.getId())
                .requester(request.getRequester().getId())
                .status(request.getStatus())
                .build();
    }
}
