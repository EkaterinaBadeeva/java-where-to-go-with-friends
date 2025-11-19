package com.my.pet.project.where_to_go_with_friends.user.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserShortDto {
    //id — уникальный идентификатор пользователя
    Long id;

    //name — имя или логин пользователя
    String name;
}
