package com.my.pet.project.where_to_go_with_friends.event.dto;

import com.my.pet.project.where_to_go_with_friends.request.dto.ParticipationRequestDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateResult {
    //Результат подтверждения/отклонения заявок на участие в событии

    //confirmedRequests — подтвержденные заявоки на участие в событии
    List<ParticipationRequestDto> confirmedRequests;

    //rejectedRequests — отклоненные заявоки на участие в событии
    List<ParticipationRequestDto> rejectedRequests;
}
