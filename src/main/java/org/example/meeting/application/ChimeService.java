package org.example.meeting.application;

import lombok.RequiredArgsConstructor;
import org.example.global.common.UuidUtils;
import org.example.global.exception.type.BadRequestException;
import org.example.meeting.domain.AttendeeSession;
import org.example.meeting.domain.dto.*;
import org.example.meeting.domain.MeetingSession;
import org.example.meeting.domain.dto.MediaPlacement;
import org.example.meeting.exception.attendee.AttendeeExceptionType;
import org.example.meeting.exception.meeting.MeetingExceptionType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import software.amazon.awssdk.services.chimesdkmeetings.ChimeSdkMeetingsClient;
import software.amazon.awssdk.services.chimesdkmeetings.model.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChimeService {

    private final ChimeSdkMeetingsClient chimeSdkMeetingsClient;
    private final MeetingSessionService meetingSessionService;
    private final AttendeeSessionService attendeeSessionService;
    private static final String MEDIA_REGION = "ap-northeast-2";

    public CreateMeetingResponseDTO createMeeting(String applyUserName, String receiveUserName) {
        // 기존 미팅 확인
        MeetingSession existingMeeting = findExistingMeeting(applyUserName, receiveUserName);
        if (existingMeeting != null) {
            return convertToMeetingResponseDTO(existingMeeting);
        }

        // 새 미팅 생성
        CreateMeetingResponse createMeetingResponse = createChimeMeeting();
        MeetingSession meetingSession = saveMeetingSession(createMeetingResponse, applyUserName, receiveUserName);

        return convertToMeetingResponseDTO(meetingSession);
    }

    private MeetingSession findExistingMeeting(String applyUserName, String receiveUserName) {
        List<MeetingSession> existingMeetings = new ArrayList<>();
        existingMeetings.addAll(meetingSessionService.listMeetings(applyUserName));
        existingMeetings.addAll(meetingSessionService.listMeetings(receiveUserName));

        return existingMeetings.stream()
                .filter(session -> session.getApplyUserName().equals(applyUserName) ||
                        session.getReceiveUserName().equals(receiveUserName))
                .findFirst()
                .orElse(null);
    }

    private CreateMeetingResponse createChimeMeeting() {
        try {
            CreateMeetingRequest request = CreateMeetingRequest.builder()
                    .clientRequestToken(UuidUtils.generateUuid())
                    .externalMeetingId(UuidUtils.generateUuid())
                    .mediaRegion(MEDIA_REGION)
                    .build();
            return chimeSdkMeetingsClient.createMeeting(request);
        } catch (Exception e) {
            throw new BadRequestException(MeetingExceptionType.NOT_CREATE_MEETING);
        }
    }

    public CreateAttendeeResponseDTO createAttendee(String meetingId) {
        String externalUserId = getCurrentUserId();

        // 기존 참석자 확인
        AttendeeSession existingAttendee = attendeeSessionService.findByExternalUserId(externalUserId)
                .orElse(null);
        if (existingAttendee != null) {
            return convertToAttendeeResponseDTO(existingAttendee);
        }

        // 새 참석자 생성
        CreateAttendeeResponse createAttendeeResponse = createChimeAttendee(meetingId, externalUserId);
        AttendeeSession attendeeSession = saveAttendeeSession(createAttendeeResponse, meetingId);

        return convertToAttendeeResponseDTO(attendeeSession);
    }

    private CreateAttendeeResponse createChimeAttendee(String meetingId, String externalUserId) {
        try {
            CreateAttendeeRequest request = CreateAttendeeRequest.builder()
                    .meetingId(meetingId)
                    .externalUserId(externalUserId)
                    .build();
            return chimeSdkMeetingsClient.createAttendee(request);
        } catch (Exception e) {
            throw new BadRequestException(AttendeeExceptionType.NOT_CREATE_ATTENDEE);
        }
    }

    public void deleteMeeting(String meetingId) {
        try {
            DeleteMeetingRequest request = DeleteMeetingRequest.builder()
                    .meetingId(meetingId)
                    .build();
            chimeSdkMeetingsClient.deleteMeeting(request);

            meetingSessionService.deleteByMeetingId(meetingId);
            attendeeSessionService.deleteByMeetingId(meetingId);
        } catch (Exception e) {
            throw new BadRequestException(MeetingExceptionType.NOT_FOUND_MEETING);
        }
    }

    public List<CreateMeetingResponseDTO> listMeetings() {
        String currentUserId = getCurrentUserId();
        return meetingSessionService.listMeetings(currentUserId).stream()
                .map(this::convertToMeetingResponseDTO)
                .collect(Collectors.toList());
    }

    private String getCurrentUserId() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    // DTO 변환 메서드들
    private CreateMeetingResponseDTO convertToMeetingResponseDTO(MeetingSession session) {
        return CreateMeetingResponseDTO.builder()
                .externalMeetingId(session.getExternalMeetingId())
                .mediaPlacement(convertToMediaPlacement(session))
                .mediaRegion(session.getMediaRegion())
                .meetingArn(session.getMeetingArn())
                .meetingId(session.getMeetingId())
                .applyUserName(session.getApplyUserName())
                .receiveUserName(session.getReceiveUserName())
                .build();
    }

    private MediaPlacement convertToMediaPlacement(MeetingSession session) {
        return MediaPlacement.builder()
                .audioFallbackUrl(session.getAudioFallbackUrl())
                .audioHostUrl(session.getAudioHostUrl())
                .eventIngestionUrl(session.getEventIngestionUrl())
                .screenDataUrl(session.getScreenDataUrl())
                .screenSharingUrl(session.getScreenSharingUrl())
                .screenViewingUrl(session.getScreenViewingUrl())
                .signalingUrl(session.getSignalingUrl())
                .turnControlUrl(session.getTurnControllerUrl())
                .build();
    }

    private CreateAttendeeResponseDTO convertToAttendeeResponseDTO(AttendeeSession session) {
        return CreateAttendeeResponseDTO.builder()
                .attendeeId(session.getAttendeeId())
                .externalUserId(session.getExternalUserId())
                .joinToken(session.getJoinToken())
                .build();
    }

    private MeetingSession saveMeetingSession(CreateMeetingResponse response, String applyUserName, String receiveUserName) {
        MeetingSession meetingSession = MeetingSession.builder()
                .externalMeetingId(response.meeting().externalMeetingId())
                .mediaRegion(response.meeting().mediaRegion())
                .meetingArn(response.meeting().meetingArn())
                .meetingId(response.meeting().meetingId())
                .audioFallbackUrl(response.meeting().mediaPlacement().audioFallbackUrl())
                .audioHostUrl(response.meeting().mediaPlacement().audioHostUrl())
                .eventIngestionUrl(response.meeting().mediaPlacement().eventIngestionUrl())
                .screenDataUrl(response.meeting().mediaPlacement().screenDataUrl())
                .screenSharingUrl(response.meeting().mediaPlacement().screenSharingUrl())
                .screenViewingUrl(response.meeting().mediaPlacement().screenViewingUrl())
                .signalingUrl(response.meeting().mediaPlacement().signalingUrl())
                .turnControllerUrl(response.meeting().mediaPlacement().turnControlUrl())
                .applyUserName(applyUserName)
                .receiveUserName(receiveUserName)
                .build();

        return meetingSessionService.save(meetingSession);
    }

    private AttendeeSession saveAttendeeSession(CreateAttendeeResponse response, String meetingId) {
        AttendeeSession attendeeSession = AttendeeSession.builder()
                .attendeeId(response.attendee().attendeeId())
                .externalUserId(getCurrentUserId())
                .joinToken(response.attendee().joinToken())
                .meetingId(meetingId)
                .build();
        attendeeSessionService.save(attendeeSession);
        return attendeeSession;
    }
}
