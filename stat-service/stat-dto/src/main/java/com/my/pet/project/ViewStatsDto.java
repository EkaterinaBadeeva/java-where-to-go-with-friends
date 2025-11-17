package com.my.pet.project;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ViewStatsDto {

    //app — название сервиса для которого запрашивается информация
    @NotBlank
    String app;

    //uri — URI для которого был осуществлен запрос
    @NotBlank
    String uri;

    //hits — количество просмотров
    @PositiveOrZero
    Long hits;
}
