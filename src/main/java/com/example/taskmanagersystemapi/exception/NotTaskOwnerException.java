package com.example.taskmanagersystemapi.exception;

public class NotTaskOwnerException extends RuntimeException {
    public NotTaskOwnerException(String message) {
        super(message);
    }
}
