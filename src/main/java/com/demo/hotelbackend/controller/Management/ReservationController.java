package com.demo.hotelbackend.controller.Management;

import com.demo.hotelbackend.Model.Collections.Reservation;
import com.demo.hotelbackend.Model.Collections.Room;
import com.demo.hotelbackend.Services.reservationService;
import com.demo.hotelbackend.data.DTOReservation;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/reservation")
public class ReservationController {

    @Autowired
    private reservationService service;

    @GetMapping("/")
    public Mono<ResponseEntity<Flux<Reservation>>> findByAll() {
        return Mono.just(ResponseEntity.accepted().body(service.findAll()));
    }

    @GetMapping("/findByAvaliable/{date_in}/{date_en}/{nguest}/{ischildren}")
    public Mono<ResponseEntity<Flux<Room>>> findByAvaliable(
        @PathVariable("date_in") String date_in,
        @PathVariable("date_en") String date_en,
        @PathVariable("nguest") int nguest,
        @PathVariable("ischildren") boolean ischildren
    ) {
        return Mono.just(
            ResponseEntity.accepted().body(service.findAvailableRooms(date_in, date_en, nguest, ischildren))
        );
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Map<String, Object>>> save(
        @RequestBody @Valid DTOReservation DTOReservation,
        BindingResult bindinResult
    ) {
        if (bindinResult.hasErrors()) return service.BindingResultErrors(bindinResult);

        return service
            .save(DTOReservation)
            .map(
                response -> {
                    return ResponseEntity.status(response.getStatus()).body(response.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/CalculateSelectedRoom/{idroom}/{date1}/{date2}")
    public Mono<ResponseEntity<Map<String, Object>>> CalculateSelectedRoom(
        @PathVariable("idroom") String idroom,
        @PathVariable("date1") String date1,
        @PathVariable("date2") String date2
    ) {
        return service
            .CalculateSelectedRoom(idroom, date1, date2)
            .map(
                response -> {
                    return ResponseEntity.status(response.getStatus()).body(response.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @PutMapping("/ChangeStateReservation/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> CalculateSelectedRoom(@PathVariable("id") String id) {
        return service
            .changeState(id)
            .map(
                response -> {
                    return ResponseEntity.status(response.getStatus()).body(response.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
