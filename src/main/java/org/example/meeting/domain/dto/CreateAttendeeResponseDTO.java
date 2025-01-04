package org.example.meeting.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateAttendeeResponseDTO {

    @NotNull(message = "attendeeId cannot be null")
    private String attendeeId;

    @NotNull(message = "externalUserId cannot be null")
    private String externalUserId;

    @NotNull(message = "joinToken cannot be null")
    private String joinToken;

    @Builder
    public CreateAttendeeResponseDTO(String attendeeId, String externalUserId, String joinToken) {
        this.attendeeId = attendeeId;
        this.externalUserId = externalUserId;
        this.joinToken = joinToken;
    }
}

