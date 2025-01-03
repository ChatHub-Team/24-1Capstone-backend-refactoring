package org.example.meeting.exception.attendee;

public class AttendeeSessionNotFoundException extends RuntimeException {

    public AttendeeSessionNotFoundException(String message) {
        super(message);
    }
}
