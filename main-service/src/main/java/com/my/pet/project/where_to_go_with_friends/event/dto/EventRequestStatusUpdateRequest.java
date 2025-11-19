package com.my.pet.project.where_to_go_with_friends.event.dto;

import com.my.pet.project.where_to_go_with_friends.request.model.ParticipationRequestStatus;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventRequestStatusUpdateRequest {
    //Изменение статуса запроса на участие в событии текущего пользователя
    @NotEmpty
    //requestIds — идентификаторы запросов на участие в событии текущего пользователя
    List<Long> requestIds;

    //status — новый статус запроса на участие в событии текущего пользователя
    @NotNull
    ParticipationRequestStatus status;
}
