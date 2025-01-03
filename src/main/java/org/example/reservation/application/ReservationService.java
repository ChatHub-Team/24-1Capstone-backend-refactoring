package org.example.reservation.application;

import lombok.RequiredArgsConstructor;
import org.example.global.exception.type.BadRequestException;
import org.example.global.exception.type.ForbiddenException;
import org.example.global.exception.type.NotFoundException;
import org.example.reservation.exception.ReservationExceptionType;
import org.example.reservation.exception.ReservationNotFoundException;
import org.example.user.exception.UserExceptionType;
import org.example.user.exception.UserNotFoundException;
import org.example.reservation.domain.dto.CreateReservationRequestDTO;
import org.example.reservation.domain.dto.ReservationDTO;
import org.example.reservation.domain.entity.Reservation;
import org.example.reservation.domain.entity.ReservationStatus;
import org.example.reservation.repository.ReservationRepository;
import org.example.user.domain.entity.member.User;
import org.example.user.repository.member.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final UserRepository userRepository;

    public List<ReservationDTO> getMyReservation() {
        String userName = getCurrentUsername();
        List<Reservation> reservations = reservationRepository.findByApplyUserUsernameOrReceiveUserUsername(userName, userName);
        if (reservations.isEmpty()) {
            throw new NotFoundException(ReservationExceptionType.NOT_FOUND_RESERVATION);
        }
        return new ReservationDTO().toDtoList(reservations);
    }

    public void createReservation(CreateReservationRequestDTO createReservationRequestDTO) {
        String applyUserName = getCurrentUsername();
        User applyUser = findUserByUsername(applyUserName);
        User receiveUser = findUserByUsername(createReservationRequestDTO.getReceiveUserName());

        try {
            Reservation reservation = Reservation.builder()
                    .applyUser(applyUser)
                    .receiveUser(receiveUser)
                    .content(createReservationRequestDTO.getContent())
                    .startTime(createReservationRequestDTO.getStartTime())
                    .endTime(createReservationRequestDTO.getEndTime())
                    .reservationStatus(ReservationStatus.PROGRESSING)
                    .build();
            reservationRepository.save(reservation);
        } catch (Exception e) {
            throw new BadRequestException(ReservationExceptionType.NOT_CREATE_RESERVATION);
        }
    }

    public void approveReservation(Long reservationId) {
        Reservation reservation = findReservationById(reservationId);
        validateReceiveUser(reservationId);

        try {
            reservation.approve();
            reservationRepository.save(reservation);
        } catch (Exception e) {
            throw new BadRequestException(ReservationExceptionType.NOT_APPROVE_RESERVATION);
        }
    }

    public void refuseReservation(Long reservationId) {
        Reservation reservation = findReservationById(reservationId);
        validateReceiveUser(reservationId);

        try {
            reservation.refuse();
            reservationRepository.save(reservation);
        } catch (Exception e) {
            throw new BadRequestException(ReservationExceptionType.NOT_REFUSE_RESERVATION);
        }
    }

    public void deleteReservation(Long reservationId) {
        Reservation reservation = findReservationById(reservationId);
        validateApplyUser(reservationId);

        try {
            reservationRepository.delete(reservation);
        } catch (Exception e) {
            throw new BadRequestException(ReservationExceptionType.NOT_DELETE_RESERVATION);
        }
    }

    public void deleteReservationByUserName(String userName) {
        try {
            reservationRepository.deleteByApplyUserUsernameOrReceiveUserUsername(userName, userName);
        } catch (Exception e) {
            throw new BadRequestException(ReservationExceptionType.NOT_DELETE_RESERVATION);
        }
    }

    private String getCurrentUsername() {
        return SecurityContextHolder.getContext().getAuthentication().getName();
    }

    private User findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new NotFoundException(UserExceptionType.NOT_FOUND_USER));
    }

    private Reservation findReservationById(Long reservationId) {
        return reservationRepository.findById(reservationId)
                .orElseThrow(() -> new NotFoundException(ReservationExceptionType.NOT_FOUND_RESERVATION));
    }

    private void validateApplyUser(Long reservationId) {
        String userName = getCurrentUsername();
        Reservation reservation = findReservationById(reservationId);

        if (!reservation.getApplyUser().getUsername().equals(userName)) {
            throw new ForbiddenException(ReservationExceptionType.NOT_AUTHORIZED_APPLY_USER);
        }
    }

    private void validateReceiveUser(Long reservationId) {
        String userName = getCurrentUsername();
        Reservation reservation = findReservationById(reservationId);

        if (!reservation.getReceiveUser().getUsername().equals(userName)) {
            throw new ForbiddenException(ReservationExceptionType.NOT_AUTHORIZED_APPLY_USER);
        }
    }
}