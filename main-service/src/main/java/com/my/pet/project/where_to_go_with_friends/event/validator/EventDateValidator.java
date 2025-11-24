package com.my.pet.project.where_to_go_with_friends.event.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import java.time.LocalDateTime;

public class EventDateValidator implements ConstraintValidator<EventDate, LocalDateTime> {
    @Override
    public boolean isValid(LocalDateTime localDateTime, ConstraintValidatorContext context) {
        return localDateTime.isAfter(LocalDateTime.now().plusHours(2));
    }
}