package com.my.pet.project.where_to_go_with_friends.rating.controller;

import com.my.pet.project.where_to_go_with_friends.rating.dto.RatingDto;
import com.my.pet.project.where_to_go_with_friends.rating.dto.RatingStateResult;
import com.my.pet.project.where_to_go_with_friends.rating.model.RatingState;
import com.my.pet.project.where_to_go_with_friends.rating.service.RatingService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/users/{userId}/ratings")
@RequiredArgsConstructor
public class PrivateRatingController {
    private final RatingService ratingService;

    //POST /users/{userId}/ratings?eventId={eventId}&state={state}
    // поставить оценку событию
    // оценку можно поставить только если пользователь участвовал в событии
    // нельзя поставить больше одной оценки одному событию
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RatingDto createRating(@PathVariable @Positive @NotNull Long userId,
                                  @RequestParam @Positive @NotNull Long eventId,
                                  @RequestParam @NotNull RatingState state) {
        return ratingService.createRatingPrivate(userId, eventId, state);
    }

    //PATCH /users/{userId}/ratings?eventId={eventId}&state={state}
    //изменить оценку событию
    @PatchMapping
    @ResponseStatus(HttpStatus.OK)
    public RatingDto updateRating(@PathVariable @Positive @NotNull Long userId,
                                  @RequestParam @Positive @NotNull Long eventId,
                                  @RequestParam  RatingState state) {
        return ratingService.updateRatingPrivate(userId, eventId, state);
    }

    //DELETE /users/{userId}/ratings?eventId={eventId}
    // удалить оценку событию
    @DeleteMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteRating(@PathVariable @Positive @NotNull Long userId,
                             @RequestParam @Positive @NotNull Long eventId) {
        ratingService.deleteRatingPrivate(userId, eventId);
    }

    //GET  /users/{userId}/ratings?eventId={eventId}
    // получить информацию о всех оценках события текущего пользователя
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public RatingStateResult getRatingOfEvent(@PathVariable @Positive @NotNull Long userId,
                                              @RequestParam @Positive @NotNull Long eventId) {
        return ratingService.getRatingOfEventPrivate(userId, eventId);
    }
}
