package com.my.pet.project.where_to_go_with_friends.event.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LocationDto {

    //широта места проведения события
    Float lat;

    //долгота места проведения события
    Float lon;
}
