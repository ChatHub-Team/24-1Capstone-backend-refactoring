package org.example.exception.type;

public class MeetingSessionNotFoundException extends RuntimeException {

    public MeetingSessionNotFoundException(String message) {
        super(message);
    }
}
