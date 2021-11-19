package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Interface.GuestRepository;
import com.demo.hotelbackend.Interface.ReservationRepository;
import com.demo.hotelbackend.Interface.RoomRepository;
import com.demo.hotelbackend.Interface.userRepository;
import com.demo.hotelbackend.Model.Collections.Guest;
import com.demo.hotelbackend.Model.Collections.Reservation;
import com.demo.hotelbackend.Model.Collections.Room;
import com.demo.hotelbackend.Model.Response;
import com.demo.hotelbackend.constants.enums;
import com.demo.hotelbackend.data.DTOReservation;
import com.demo.hotelbackend.data.ResponseReservation;
import com.demo.hotelbackend.logic.Logic;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class reservationService {

    @Autowired
    private ReservationRepository reservationInterface;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private typeRoomService typeRoomService;

    @Autowired
    private GuestRepository guestInterface;

    @Autowired
    private userRepository userRepository;

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

    public Flux<Room> findAvailableRooms(String date1, String date2, int nguest, boolean ischildren) {
        if (Logic.convertDate(date1).before(Logic.convertDate(date2))) {
            Set<String> listIDrooms = findAll()
                .toStream()
                .filter(res -> Logic.verifyCross(date1, date2, res.getDate_ini(), res.getDate_end()))
                .map(Reservation::getIdroom)
                .collect(Collectors.toSet());

            return Flux.fromIterable(
                roomRepository
                    .findAll()
                    .toStream()
                    .filter(ro -> !listIDrooms.contains(ro.getIdroomm()))
                    .filter(ro -> ro.getChildren().equals(ischildren))
                    .filter(ro -> typeRoomService.findByIdtyperoom(ro.getIdtype()).block().getNumberGuest() >= nguest)
                    .filter(ro -> ro.getState())
                    .collect(Collectors.toList())
            );
        }
        return null;
    }

    public Mono<Response> CalculateSelectedRoom(String idroom, String date1, String date2) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;
        int dif = Logic.DifferenceOfDaysBetweenDates(date1, date2);
        Optional<Room> room = roomRepository
            .findAll()
            .toStream()
            .filter(r -> r.getIdroomm().equals(idroom))
            .findFirst();
        Double subtotal = room.get().getPrice() * dif;
        Double tax = subtotal / 18;
        Double total = subtotal + tax;

        ResponseReservation resp = new ResponseReservation(date1, dif, subtotal, tax, total);
        return Mono.just(new Response(message, resp, status));
    }

    public Mono<Response> save(DTOReservation DTOReservation) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;

        String id = DTOReservation.getId() == null ? "" : DTOReservation.getId();
        DTOReservation.setId(id);

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

        Reservation res = new Reservation(
            Logic.convertDate(DTOReservation.getDate_ini()),
            Logic.convertDate(DTOReservation.getDate_end()),
            DTOReservation.getRequirements(),
            DTOReservation.getDni(),
            DTOReservation.getIdroom(),
            DTOReservation.getIduser(),
            DTOReservation.getSubtotal(),
            DTOReservation.getTax(),
            DTOReservation.getTotal()
        );

        try {
            Logic.sendMail(
                DTOReservation.getEmail(),
                "HOTEL EL VIAJERO.",
                "Su reservación a sido registrada, a espera de ser aceptada " + DTOReservation.getDate_ini()
            );
        } catch (Exception e) {
            // TODO: handle exception
        }

        reservationInterface.save(res).subscribe();

        return Mono.just(new Response(message, res, status));
    }

    public Mono<Response> changeState(String id) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;
        String mssg = "Su reserva a sido aceptada.";

        Mono<Reservation> res = reservationInterface.findById(id);

        if (res.block() != null) {
            Boolean state = res.block().getState();
            String email = userRepository.findById(res.block().getIduser()).block().getEmail();

            res.block().setState(!state);
            reservationInterface.save(res.block()).subscribe();

            if (state) mssg = "Su cita a sido cancelada.";
            try {
                Logic.sendMail(email, "HOTEL EL VIAJERO.", mssg);
            } catch (Exception e) {
                // TODO: handle exception
            }
        } else {
            status = HttpStatus.NOT_FOUND;
            message = enums.Messages.INVALID_DATA;
        }

        return Mono.just(new Response(message, status));
    }
}
