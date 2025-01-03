package org.example.meeting.exception.meeting;

public class MeetingSessionNotFoundException extends RuntimeException {

    public MeetingSessionNotFoundException(String message) {
        super(message);
    }
}
