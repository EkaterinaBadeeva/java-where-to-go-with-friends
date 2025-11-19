package com.my.pet.project.where_to_go_with_friends.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.pet.project.where_to_go_with_friends.event.model.StateAction;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateEventAdminRequest {
    //annotation — краткое описание события
    @Size(min = 20, max = 2000, message = "Длина краткого описания события должна быть от 20 до 2000 символов")
    String annotation;

    //category — id категории к которой относится событие
    Long category;

    //description — полное описание события
    @Size(min = 20, max = 7000, message = "Длина полного описания события должна быть от 20 до 2000 символов")
    String description;

    //eventDate — дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    //location — широта и долгота места проведения события
    LocationDto location;

    //paid — нужно ли оплачивать участие
    Boolean paid;

    //participantLimit — ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @PositiveOrZero
    Integer participantLimit;

    //requestModeration — нужна ли пре-модерация заявок на участие
    Boolean requestModeration;

    //state — список состояний жизненного цикла события
    StateAction stateAction;

    //title — заголовок
    @Size(min = 3, max = 120, message = "Длина заголовка должна быть от 3 до 120 символов")
    String title;
}
