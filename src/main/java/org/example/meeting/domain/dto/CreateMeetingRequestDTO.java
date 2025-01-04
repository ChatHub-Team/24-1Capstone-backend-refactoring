package org.example.meeting.domain.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CreateMeetingRequestDTO {

    @NotNull(message = "applyUserName cannot be null")
    private String applyUserName;

    @NotNull(message = "receiveUserName cannot be null")
    private String receiveUserName;

    @Builder
    public CreateMeetingRequestDTO(String applyUserName, String receiveUserName) {
        this.applyUserName = applyUserName;
        this.receiveUserName = receiveUserName;
    }
}
