package com.my.pet.project.where_to_go_with_friends.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.pet.project.where_to_go_with_friends.category.dto.CategoryDto;
import com.my.pet.project.where_to_go_with_friends.event.model.EventState;
import com.my.pet.project.where_to_go_with_friends.user.dto.UserShortDto;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EventFullDto {
    //annotation — краткое описание события
    String annotation;

    //category — категория
    CategoryDto category;

    //confirmedRequests — количество одобренных заявок на участие в данном событии
    @Builder.Default
    Integer confirmedRequests = 0;

    //createdOn — дата и время создания события (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime createdOn;

    //description — полное описание события
    String description;

    //eventDate — дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    //id — уникальный идентификатор события
    Long id;

    //initiator — пользователь (краткая информация)
    UserShortDto initiator;

    //location — широта и долгота места проведения события
    LocationDto location;

    //paid — нужно ли оплачивать участие
    Boolean paid;

    //participantLimit — ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @Builder.Default
    Integer participantLimit = 0;

    //publishedOn — дата и время публикации события (в формате "yyyy-MM-dd HH:mm:ss")
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime publishedOn;

    //requestModeration — нужна ли пре-модерация заявок на участие
    Boolean requestModeration;

    //state — список состояний жизненного цикла события
    EventState state;

    //title — заголовок
    String title;

    //views — количество просмотрев события
    @Builder.Default
    Long views = 0L;
}
