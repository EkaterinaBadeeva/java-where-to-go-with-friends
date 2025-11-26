package com.my.pet.project.where_to_go_with_friends.rating.service;


import com.my.pet.project.where_to_go_with_friends.rating.dto.RatingDto;
import com.my.pet.project.where_to_go_with_friends.rating.dto.RatingStateResult;
import com.my.pet.project.where_to_go_with_friends.rating.model.RatingState;

public interface RatingService {
    RatingDto createRatingPrivate(Long userId, Long eventId, RatingState state);

    RatingDto updateRatingPrivate(Long userId, Long eventId, RatingState state);

    void deleteRatingPrivate(Long userId, Long eventId);

    RatingStateResult getRatingOfEventPrivate(Long userId, Long eventId);
}
