package com.my.pet.project.where_to_go_with_friends.rating.dto;

import com.my.pet.project.where_to_go_with_friends.rating.model.RatingState;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RatingDto {

    //event — событие, которое оценил пользователь
    Long event;

    //user — пользователь, который поставил оценку
    Long user;

    //state — состояние оценки (like/dislike)
    RatingState state;
}
