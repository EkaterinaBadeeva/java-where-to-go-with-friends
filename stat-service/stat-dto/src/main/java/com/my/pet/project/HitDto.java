package com.my.pet.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HitDto {

    //app — идентификатор сервиса для которого записывается информация
    @NotBlank
    String app;

    //uri — URI для которого был осуществлен запрос
    @NotBlank
    String uri;

    //ip — IP-адрес пользователя, осуществившего запрос
    @NotBlank
    String ip;

    //timestamp — дата и время, когда был совершен запрос к эндпоинту (в формате "yyyy-MM-dd HH:mm:ss")
    @NotNull
    @PastOrPresent
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    LocalDateTime timestamp;
}
