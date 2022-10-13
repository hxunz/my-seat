package com.codesoom.myseat.controllers;

import com.codesoom.myseat.domain.User;
import com.codesoom.myseat.dto.ReservationRequest;
import com.codesoom.myseat.exceptions.AlreadyReservedException;
import com.codesoom.myseat.security.UserAuthentication;
import com.codesoom.myseat.services.ReservationService;
import com.codesoom.myseat.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

/**
 * 좌석 예약 요청 컨트롤러
 */
@RestController
@RequestMapping("/reservations")
@CrossOrigin
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;
    private final UserService userService;

    public ReservationController(
            ReservationService reservationService, 
            UserService userService
    ) {
        this.reservationService = reservationService;
        this.userService = userService;
    }

    /**
     * 좌석을 예약하고 상태 코드 204를 응답합니다.
     * 
     * @param request 예약 폼에 입력된 데이터
     * @throws AlreadyReservedException 방문 일자에 대한 예약 내역이 이미 존재하면 던집니다.
     */
    @PostMapping
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("isAuthenticated()")
    public void reserve(
            @RequestBody ReservationRequest request
    ) {
        String email = ((UserAuthentication) SecurityContextHolder.getContext().getAuthentication()).getEmail();
        User user = userService.findUser(email);

        String date = request.getDate();
        String content = request.getContent();

        reservationService.createReservation(user, date, content);
    }
}