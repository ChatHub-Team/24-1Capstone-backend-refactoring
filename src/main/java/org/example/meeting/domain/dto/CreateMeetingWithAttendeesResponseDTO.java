package org.example.meeting.domain.dto;

import lombok.Builder;
import lombok.Getter;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;

@Getter
public class CreateMeetingWithAttendeesResponseDTO {

    @NotNull(message = "External meeting ID is required")
    private String externalMeetingId;

    private MediaPlacement mediaPlacement;

    @NotNull(message = "Media region is required")
    private String mediaRegion;

    @NotNull(message = "Meeting ARN is required")
    private String meetingArn;

    @NotNull(message = "Meeting ID is required")
    private String meetingId;

    @NotNull(message = "Attendees list cannot be null")
    @Size(min = 1, message = "At least one attendee is required")
    private List<CreateAttendeeResponseDTO> attendees;

    @Builder
    public CreateMeetingWithAttendeesResponseDTO(String externalMeetingId, MediaPlacement mediaPlacement, String mediaRegion, String meetingArn, String meetingId, List<CreateAttendeeResponseDTO> attendees) {
        this.externalMeetingId = externalMeetingId;
        this.mediaPlacement = mediaPlacement;
        this.mediaRegion = mediaRegion;
        this.meetingArn = meetingArn;
        this.meetingId = meetingId;
        this.attendees = attendees;
    }
}
