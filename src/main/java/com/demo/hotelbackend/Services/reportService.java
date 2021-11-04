package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Model.Collections.Reservation;
import com.demo.hotelbackend.Model.Collections.TypeRoom;
import com.demo.hotelbackend.Model.Collections.user;
import com.demo.hotelbackend.Model.Response;
import com.demo.hotelbackend.constants.enums;
import com.demo.hotelbackend.data.DTOReportsType;
import com.demo.hotelbackend.logic.Logic;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class reportService {

    @Autowired
    private reservationService reservationService;

    @Autowired
    private typeRoomService typeRoomService;

    @Autowired
    private roomService roomService;

    @Autowired
    private userService userService;

    private int count(String TYPE) {
        return (int) reservationService
            .findAll()
            .toStream()
            .filter(res ->
                typeRoomService
                    .findByIdtyperoom(roomService.findByIdroomm(res.getIdroom()).block().getIdtype())
                    .block()
                    .getName()
                    .equals(TYPE)
            )
            .count();
    }

    public Mono<Response> mostUsedRoomTypes() {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;

        List<String> types = typeRoomService.findAll().toStream().map(TypeRoom::getName).collect(Collectors.toList());
        DTOReportsType DTOReportsType = new DTOReportsType();

        for (int i = 0; i < types.size(); i++) {
            DTOReportsType.getCount().add(count(types.get(i)));
            DTOReportsType.getTypes().add(types.get(i));
        }

        return Mono.just(new Response(message, DTOReportsType, status));
    }

    public Mono<Response> totalReservations() {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;

        int all = (int) reservationService.findAll().toStream().count();

        return Mono.just(new Response(message, all, status));
    }

    public Mono<Response> promReservationsTime() {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;

        List<Reservation> list = reservationService.findAll().toStream().collect(Collectors.toList());
        Double prom = 0.0;

        for (int i = 0; i < list.size(); i++) {
            prom = Logic.DifferenceOfDaysBetweenDates2(list.get(i).getDate_end(), list.get(i).getDate_ini()) + prom;
        }
        prom = prom / list.size();

        return Mono.just(new Response(message, prom, status));
    }

    public Mono<Response> SeeEarningsSoFar() {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;
        Double prom = reservationService.findAll().toStream().mapToDouble(Reservation::getTotal).sum();

        return Mono.just(new Response(message, prom, status));
    }

    public Mono<Response> SeeEarningsByDate(String date1, String date2) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;
        Double prom = reservationService
            .findAll()
            .toStream()
            .filter(res -> res.getDate_ini().before(Logic.convertDate(date1)))
            .filter(res -> res.getDate_end().after(Logic.convertDate(date2)))
            .mapToDouble(Reservation::getTotal)
            .sum();

        return Mono.just(new Response(message, prom, status));
    }

    public Mono<Response> UserWithMoreReservations() {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;
        //int size = (int) reservationService.findAll().toStream().count();
        List<String> idUsers = userService
            .findAll()
            .toStream()
            .filter(user -> !user.getRoles().contains(enums.ROLE_HUESPED.name()))
            .map(user::getIdaccount)
            .collect(Collectors.toList());

        System.out.println(
            reservationService
                .findAll()
                .toStream()
                .map(Reservation::getIduser)
                .filter(userid -> idUsers.contains(userid))
                .count()
        );
        return Mono.just(new Response(message, 0, status));
    }
}
