package org.example.meeting.application;

import lombok.RequiredArgsConstructor;
import org.example.global.exception.type.BadRequestException;
import org.example.global.exception.type.NotFoundException;
import org.example.meeting.domain.MeetingSession;
import org.example.meeting.exception.meeting.MeetingExceptionType;
import org.example.meeting.repository.MeetingSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class MeetingSessionService {

    private final MeetingSessionRepository meetingSessionRepository;

    public MeetingSession save(MeetingSession meetingSession) {
        try {
            return meetingSessionRepository.save(meetingSession);
        } catch (Exception e) {
            throw new BadRequestException(MeetingExceptionType.NOT_CREATE_MEETING);
        }
    }

    public List<MeetingSession> listMeetings(String userName) {
        List<MeetingSession> meetingSessions = meetingSessionRepository.findByApplyUserNameOrReceiveUserName(userName);
        if (meetingSessions.isEmpty()) {
            throw new NotFoundException(MeetingExceptionType.NOT_FOUND_MEETING);
        }
        return meetingSessions;
    }

    public void deleteByMeetingId(String meetingId) {
        validateMeetingExists(meetingId);
        meetingSessionRepository.deleteByMeetingId(meetingId);
    }

    public void deleteByMeetingSessionId(Long meetingSessionId) {
        validateMeetingSessionExists(meetingSessionId);
        meetingSessionRepository.deleteById(meetingSessionId);
    }

    private void validateMeetingExists(String meetingId) {
        if (!meetingSessionRepository.existsByMeetingId(meetingId)) {
            throw new NotFoundException(MeetingExceptionType.NOT_FOUND_MEETING);
        }
    }

    private void validateMeetingSessionExists(Long meetingSessionId) {
        if (!meetingSessionRepository.existsById(meetingSessionId)) {
            throw new NotFoundException(MeetingExceptionType.NOT_FOUND_MEETING);
        }
    }
}