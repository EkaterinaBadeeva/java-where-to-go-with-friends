package com.my.pet.project.where_to_go_with_friends.event.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.my.pet.project.where_to_go_with_friends.event.validator.EventDate;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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
public class NewEventDto {
    //annotation — краткое описание события
    @NotBlank(message = "Краткое описание события должно быть указано")
    @Size(min = 20, max = 2000, message = "Длина краткого описания события должна быть от 20 до 2000 символов")
    String annotation;

    //category — id категории к которой относится событие
    @NotNull(message = "Id категории к которой относится событие должно быть указано")
    Long category;

    //description — полное описание события
    @NotBlank(message = "Полное описание события должно быть указано")
    @Size(min = 20, max = 7000, message = "Длина полного описания события должна быть от 20 до 2000 символов")
    String description;

    //eventDate — дата и время на которые намечено событие (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull(message = "Дата и время на которые намечено событие должны быть указаны")
    @EventDate
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime eventDate;

    //location — широта и долгота места проведения события
    @NotNull(message = "Широта и долгота места проведения события должны быть указаны")
    LocationDto location;

    //paid — нужно ли оплачивать участие
    @Builder.Default
    Boolean paid = false;

    //participantLimit — ограничение на количество участников. Значение 0 - означает отсутствие ограничения
    @PositiveOrZero
    @Builder.Default
    Integer participantLimit = 0;

    //requestModeration — нужна ли пре-модерация заявок на участие
    @Builder.Default
    Boolean requestModeration = true;

    //title — заголовок
    @NotBlank(message = "Заголовок события должен быть указан")
    @Size(min = 3, max = 120, message = "Длина заголовка должна быть от 3 до 120 символов")
    String title;
}
