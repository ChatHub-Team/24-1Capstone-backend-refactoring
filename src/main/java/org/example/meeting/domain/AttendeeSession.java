package org.example.meeting.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AttendeeSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "attendee_id", nullable = false, unique = true)
    private String attendeeId;

    @Column(name = "external_user_id", nullable = false)
    private String externalUserId;

    @Column(name = "join_token")
    private String joinToken;

    @Column(name = "meeting_id")
    private String meetingId;

    @Builder
    public AttendeeSession(String attendeeId, String externalUserId, String joinToken, String meetingId) {
        this.attendeeId = attendeeId;
        this.externalUserId = externalUserId;
        this.joinToken = joinToken;
        this.meetingId = meetingId;
    }
}
