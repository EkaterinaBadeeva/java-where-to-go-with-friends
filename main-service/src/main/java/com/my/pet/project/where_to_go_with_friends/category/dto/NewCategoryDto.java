package com.my.pet.project.where_to_go_with_friends.category.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class NewCategoryDto {

    //name — название категории
    @NotBlank(message = "Название категории должно быть указано")
    @Size(min = 1, max = 50, message = "Длина названия категории должна быть от 1 до 50 символов")
    String name;
}
