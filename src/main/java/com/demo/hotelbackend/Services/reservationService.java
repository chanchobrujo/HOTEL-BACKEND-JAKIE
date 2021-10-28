package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Interface.GuestInterface;
import com.demo.hotelbackend.Interface.ReservationInterface;
import com.demo.hotelbackend.Model.Collections.Guest;
import com.demo.hotelbackend.Model.Collections.Reservation;
import com.demo.hotelbackend.Model.Collections.Room;
import com.demo.hotelbackend.Model.Response;
import com.demo.hotelbackend.constants.enums;
import com.demo.hotelbackend.data.DTOReservation;
import com.demo.hotelbackend.logic.Logic;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public class reservationService {

    @Autowired
    private ReservationInterface reservationInterface;

    @Autowired
    private GuestInterface guestInterface;

    public Mono<ResponseEntity<Map<String, Object>>> BindingResultErrors(BindingResult bindinResult) {
        Response response = new Response(
            bindinResult.getAllErrors().stream().findFirst().get().getDefaultMessage().toString(),
            null,
            HttpStatus.NOT_ACCEPTABLE
        );

        return Mono.just(ResponseEntity.internalServerError().body(response.getResponse()));
    }

    public Flux<Reservation> findAll() {
        return reservationInterface.findAll();
    }

    public Flux<Room> findAvailableRooms(String date1, String date2) {
        List<String> listIDrooms = findAll()
            .toStream()
            .filter(
                res ->
                    !(
                        res.getDate_ini().equals(Logic.convertDate(date1)) ||
                        res.getDate_end().equals(Logic.convertDate(date2))
                    )
            )
            .map(Reservation::getIdroom)
            .collect(Collectors.toList());

        listIDrooms.forEach(System.out::println);

        return null;
    }

    public Mono<Response> save(DTOReservation DTOReservation) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = enums.Messages.INCORRECT_DATA;

        String id = DTOReservation.getId() == null ? "" : DTOReservation.getId();
        DTOReservation.setId(id);

        if (!Logic.compareDates(DTOReservation.getDate_ini(), DTOReservation.getEmail())) {
            message = enums.Messages.ERROR_DATE;
            return Mono.just(new Response(message, null, status));
        }

        Optional<Guest> guestFil = guestInterface
            .findAll()
            .toStream()
            .filter(guest -> guest.getDni().equals(DTOReservation.getDni()))
            .findFirst();

        if (guestFil.isEmpty()) {
            Guest guest = new Guest(
                DTOReservation.getDni(),
                DTOReservation.getFirtsname(),
                DTOReservation.getLastname(),
                DTOReservation.getEmail(),
                DTOReservation.getPhone()
            );
            guestInterface.save(guest).subscribe();
        }

        return Mono.just(new Response(message, null, status));
    }
}
