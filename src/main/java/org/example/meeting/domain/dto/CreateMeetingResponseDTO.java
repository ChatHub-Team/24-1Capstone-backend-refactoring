package org.example.meeting.domain.dto;

import lombok.Builder;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

@Getter
public class CreateMeetingResponseDTO {

    @NotNull(message = "External meeting ID is required")
    private String externalMeetingId;

    private MediaPlacement mediaPlacement;

    @NotNull(message = "Media region is required")
    private String mediaRegion;

    @NotNull(message = "Meeting ARN is required")
    private String meetingArn;

    @NotNull(message = "Meeting ID is required")
    private String meetingId;

    @NotNull(message = "Apply user name is required")
    private String applyUserName;

    @NotNull(message = "Receive user name is required")
    private String receiveUserName;

    @Builder
    public CreateMeetingResponseDTO(String externalMeetingId, MediaPlacement mediaPlacement, String mediaRegion, String meetingArn, String meetingId, String applyUserName, String receiveUserName) {
        this.externalMeetingId = externalMeetingId;
        this.mediaPlacement = mediaPlacement;
        this.mediaRegion = mediaRegion;
        this.meetingArn = meetingArn;
        this.meetingId = meetingId;
        this.applyUserName = applyUserName;
        this.receiveUserName = receiveUserName;
    }
}
