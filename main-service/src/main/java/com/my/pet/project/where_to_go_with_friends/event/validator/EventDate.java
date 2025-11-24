package com.my.pet.project.where_to_go_with_friends.event.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EventDateValidator.class)
public @interface EventDate {
    String message() default "Дата и время на которые намечено событие не может быть раньше, " +
            "чем через два часа от текущего момента";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}