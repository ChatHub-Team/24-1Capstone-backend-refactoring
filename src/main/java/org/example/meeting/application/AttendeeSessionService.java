package org.example.meeting.application;

import lombok.RequiredArgsConstructor;
import org.example.global.exception.type.NotFoundException;
import org.example.meeting.domain.AttendeeSession;
import org.example.meeting.exception.attendee.AttendeeExceptionType;
import org.example.meeting.repository.AttendeeSessionRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AttendeeSessionService {

    private final AttendeeSessionRepository attendeeSessionRepository;

    public void save(AttendeeSession attendeeSession) {
        attendeeSessionRepository.save(AttendeeSession.builder()
                .attendeeId(attendeeSession.getAttendeeId())
                .externalUserId(attendeeSession.getExternalUserId())
                .joinToken(attendeeSession.getJoinToken())
                .meetingId(attendeeSession.getMeetingId())
                .build());
    }

    public Optional<AttendeeSession> findByExternalUserId(String externalUserId) {
            return attendeeSessionRepository.findByExternalUserId(externalUserId);
    }

    public void deleteByMeetingId(String meetingId) {
        if (!attendeeSessionRepository.existsByMeetingId(meetingId)) {
            throw new NotFoundException(AttendeeExceptionType.NOT_FOUND_ATTENDEE);
        }
        attendeeSessionRepository.deleteByMeetingId(meetingId);
    }

    public void deleteByExternalUserId(String externalUserId) {
        if (!attendeeSessionRepository.existsByExternalUserId(externalUserId)) {
            throw new NotFoundException(AttendeeExceptionType.NOT_FOUND_ATTENDEE);
        }
        attendeeSessionRepository.deleteByExternalUserId(externalUserId);
    }
}