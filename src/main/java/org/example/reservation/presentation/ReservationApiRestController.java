package org.example.reservation.presentation;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.example.reservation.application.ReservationService;
import org.example.reservation.domain.dto.CreateReservationRequestDTO;
import org.example.reservation.domain.dto.ReservationDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "reservation-api-controller", description = "예약 처리 컨트롤러")
@RequiredArgsConstructor
@RestController
public class ReservationApiRestController {

    private final ReservationService reservationService;

    @Operation(summary = "내 예약들 조회", description = "내 예약들 조회")
    @GetMapping("api/reservation")
    public ResponseEntity<List<ReservationDTO>> getMyReservation() {
        List<ReservationDTO> reservations = reservationService.getMyReservation();
        return ResponseEntity.ok(reservations);
    }

    @Operation(summary = "새로운 예약 신청", description = "새로운 예약 신청")
    @PostMapping("/api/reservation/")
    public ResponseEntity<Void> createReservation(@RequestBody CreateReservationRequestDTO createReservationRequestDTO) {
        reservationService.createReservation(createReservationRequestDTO);
        return ResponseEntity.status(201).build();
    }

    @Operation(summary = "신청받은 매칭 승인", description = "신청받은 매칭 승인")
    @PostMapping("/api/reservation/approve/{reservationId}")
    public ResponseEntity<Void> approveReservation(@PathVariable Long reservationId) {
        reservationService.approveReservation(reservationId);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "신청받은 매칭 거절", description = "신청받은 매칭 거절")
    @PostMapping("/api/reservation/refuse/{reservationId}")
    public ResponseEntity<Void> refuseReservation(@PathVariable Long reservationId) {
        reservationService.refuseReservation(reservationId);
        return ResponseEntity.status(204).build();
    }

    @Operation(summary = "작성자가 본인 예약 취소", description = "작성자가 본인 예약 취소")
    @PostMapping("/api/reservation/delete/{reservationId}")
    public ResponseEntity<Void> deleteReservation(@PathVariable Long reservationId) {
        reservationService.deleteReservation(reservationId);
        return ResponseEntity.status(204).build();
    }
}