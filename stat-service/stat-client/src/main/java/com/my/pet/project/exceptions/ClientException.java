package com.my.pet.project.exceptions;

public class ClientException extends RuntimeException {
    public ClientException(String message) {
        super(message);
    }
}