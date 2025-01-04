package org.example.reservation.domain.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CreateReservationRequestDTO {

    @NotBlank(message = "Content cannot be blank")
    private String content;

    @NotNull(message = "Start time cannot be null")
    private LocalDateTime startTime;

    @NotNull(message = "End time cannot be null")
    private LocalDateTime endTime;

    @NotBlank(message = "Receive user name cannot be blank")
    private String receiveUserName;

    @Builder
    public CreateReservationRequestDTO(String content, LocalDateTime startTime, LocalDateTime endTime, String receiveUserName) {
        this.content = content;
        this.startTime = startTime;
        this.endTime = endTime;
        this.receiveUserName = receiveUserName;
    }
}
