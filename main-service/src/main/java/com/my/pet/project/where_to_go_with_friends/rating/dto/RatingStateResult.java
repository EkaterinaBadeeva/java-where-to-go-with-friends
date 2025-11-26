package com.my.pet.project.where_to_go_with_friends.rating.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingStateResult {
    //Результат оценок за событие текущего пользователя

    //event — событие, которое оценил пользователь
    Long event;
    String eventTitle;

    int sumLikes;
    int sumDislikes;
    int rating;

    //likes — положительные оценки за событие текущего пользователя
    List<RatingShortDto> likes;

    //dislikes — отклоненные заявоки на участие в событии
    List<RatingShortDto> dislikes;
}
