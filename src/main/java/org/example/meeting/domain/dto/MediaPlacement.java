package org.example.meeting.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MediaPlacement {

    @NotBlank(message = "Audio Fallback URL cannot be blank")
    private String audioFallbackUrl;

    @NotBlank(message = "Audio Host URL cannot be blank")
    private String audioHostUrl;

    @NotBlank(message = "Event Ingestion URL cannot be blank")
    private String eventIngestionUrl;

    @NotBlank(message = "Screen Data URL cannot be blank")
    private String screenDataUrl;

    @NotBlank(message = "Screen Sharing URL cannot be blank")
    private String screenSharingUrl;

    @NotBlank(message = "Screen Viewing URL cannot be blank")
    private String screenViewingUrl;

    @NotBlank(message = "Signaling URL cannot be blank")
    private String signalingUrl;

    @NotBlank(message = "Turn Control URL cannot be blank")
    private String turnControlUrl;

    @Builder
    public MediaPlacement(String audioFallbackUrl, String audioHostUrl, String eventIngestionUrl, String screenDataUrl, String screenSharingUrl, String screenViewingUrl, String signalingUrl, String turnControlUrl) {
        this.audioFallbackUrl = audioFallbackUrl;
        this.audioHostUrl = audioHostUrl;
        this.eventIngestionUrl = eventIngestionUrl;
        this.screenDataUrl = screenDataUrl;
        this.screenSharingUrl = screenSharingUrl;
        this.screenViewingUrl = screenViewingUrl;
        this.signalingUrl = signalingUrl;
        this.turnControlUrl = turnControlUrl;
    }
}
