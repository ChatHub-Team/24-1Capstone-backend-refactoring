package org.example.meeting.application;

import lombok.RequiredArgsConstructor;
import org.example.meeting.exception.meeting.MeetingSessionCreationException;
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
            throw new MeetingSessionCreationException("Error saving meeting session: " + e.getMessage());
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
        MeetingSession meetingSession = meetingSessionRepository.findByMeetingId(meetingId).orElseThrow(
                () -> new NotFoundException(MeetingExceptionType.NOT_FOUND_MEETING));

            meetingSessionRepository.deleteByMeetingId(meetingId);
        }

    public void deleteByMeetingSessionId(Long meetingSessionId) {
        MeetingSession meetingSession = meetingSessionRepository.findById(meetingSessionId).orElseThrow(
                () -> new NotFoundException(MeetingExceptionType.NOT_FOUND_MEETING));

        meetingSessionRepository.deleteById(meetingSessionId);
    }
}
