package org.example.meeting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MeetingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "external_meeting_id", nullable = false, unique = true)
    private String externalMeetingId;

    @Column(name = "audio_fallback_url", nullable = false)
    private String audioFallbackUrl;

    @Column(name = "audio_host_url", nullable = false)
    private String audioHostUrl;

    @Column(name = "event_ingestion_url", nullable = false)
    private String eventIngestionUrl;

    @Column(name = "screen_data_url", nullable = false)
    private String screenDataUrl;

    @Column(name = "screen_sharing_url", nullable = false)
    private String screenSharingUrl;

    @Column(name = "screen_viewing_url", nullable = false)
    private String screenViewingUrl;

    @Column(name = "signaling_url", nullable = false)
    private String signalingUrl;

    @Column(name = "turn_controller_url", nullable = false)
    private String turnControllerUrl;

    @Column(name = "media_region", nullable = false)
    private String mediaRegion;

    @Column(name = "meeting_arn", nullable = false, unique = true)
    private String meetingArn;

    @Column(name = "meeting_id", nullable = false, unique = true)
    private String meetingId;

    @Column(name = "apply_user_name", nullable = false)
    private String applyUserName;

    @Column(name = "receive_user_name", nullable = false)
    private String receiveUserName;

    @Builder
    public MeetingSession(String externalMeetingId, String audioFallbackUrl, String audioHostUrl, String eventIngestionUrl, String screenDataUrl, String screenSharingUrl, String screenViewingUrl, String signalingUrl, String turnControllerUrl, String mediaRegion, String meetingArn, String meetingId, String applyUserName, String receiveUserName) {
        this.externalMeetingId = externalMeetingId;
        this.audioFallbackUrl = audioFallbackUrl;
        this.audioHostUrl = audioHostUrl;
        this.eventIngestionUrl = eventIngestionUrl;
        this.screenDataUrl = screenDataUrl;
        this.screenSharingUrl = screenSharingUrl;
        this.screenViewingUrl = screenViewingUrl;
        this.signalingUrl = signalingUrl;
        this.turnControllerUrl = turnControllerUrl;
        this.mediaRegion = mediaRegion;
        this.meetingArn = meetingArn;
        this.meetingId = meetingId;
        this.applyUserName = applyUserName;
        this.receiveUserName = receiveUserName;
    }
}
