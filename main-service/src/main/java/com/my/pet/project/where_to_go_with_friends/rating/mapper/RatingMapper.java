package com.my.pet.project.where_to_go_with_friends.rating.mapper;

import com.my.pet.project.where_to_go_with_friends.rating.dto.RatingDto;
import com.my.pet.project.where_to_go_with_friends.rating.dto.RatingShortDto;
import com.my.pet.project.where_to_go_with_friends.rating.model.Rating;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RatingMapper {

    public static RatingDto mapToRatingDto(Rating rating) {
        return RatingDto.builder()
                .event(rating.getEvent().getId())
                .user(rating.getUser().getId())
                .state(rating.getState())
                .build();
    }

    public static RatingShortDto mapToRatingShortDto(Rating rating) {
        return RatingShortDto.builder()
                .user(rating.getUser().getName())
                .state(rating.getState())
                .build();
    }
}
