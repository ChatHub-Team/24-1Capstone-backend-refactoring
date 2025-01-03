package org.example.exception.type;

public class AttendeeSessionNotFoundException extends RuntimeException {

    public AttendeeSessionNotFoundException(String message) {
        super(message);
    }
}
