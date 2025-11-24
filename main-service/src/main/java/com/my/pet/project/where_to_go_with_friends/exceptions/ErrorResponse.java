package com.my.pet.project.where_to_go_with_friends.exceptions;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
class ErrorResponse {
    private final List<String> errors;
        private String message;
        private String reason;
        private String status;

        @Builder.Default
        @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
        private LocalDateTime timestamp = LocalDateTime.now();
}