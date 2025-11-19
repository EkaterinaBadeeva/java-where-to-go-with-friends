package com.my.pet.project.where_to_go_with_friends.compilation.dto;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UpdateCompilationRequest {
    //Изменение информации о подборке событий.

    //events — список id событий подборки для полной замены текущего списка
    List<Long> events;

    //pinned — закреплена ли подборка на главной странице сайта
    Boolean pinned;

    //title — заголовок подборки
    @Size(min = 1, max = 50, message = "Длина заголовока подборки должна быть от 1 до 50 символов")
    String title;
}
