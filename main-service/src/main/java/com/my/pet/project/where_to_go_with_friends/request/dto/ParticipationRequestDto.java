package com.my.pet.project.where_to_go_with_friends.request.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.pet.project.where_to_go_with_friends.request.model.ParticipationRequestStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ParticipationRequestDto {
    //created — дата и время создания заявки (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime created;

    //event — идентификатор события
    Long event;

    //id — уникальный идентификатор заявки
    Long id;

    //requester — идентификатор пользователя, отправившего заявку
    Long requester;

    //status — статус заявки
    ParticipationRequestStatus status;
}
