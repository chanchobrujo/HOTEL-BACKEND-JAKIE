package com.demo.hotelbackend.controller.Reports;

import com.demo.hotelbackend.Services.reportService;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/report")
public class Reports {

    @Autowired
    private reportService reportService;

    @GetMapping("/mostUsedRoomTypes")
    public Mono<ResponseEntity<Map<String, Object>>> mostUsedRoomTypes() {
        return reportService
            .mostUsedRoomTypes()
            .map(response -> {
                return ResponseEntity.status(response.getStatus()).body(response.getResponse());
            })
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/allReservations")
    public Mono<ResponseEntity<Map<String, Object>>> totalReservations() {
        return reportService
            .totalReservations()
            .map(response -> {
                return ResponseEntity.status(response.getStatus()).body(response.getResponse());
            })
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @GetMapping("/promReservationsTime")
    public Mono<ResponseEntity<Map<String, Object>>> promReservationsTime() {
        return reportService
            .promReservationsTime()
            .map(response -> {
                return ResponseEntity.status(response.getStatus()).body(response.getResponse());
            })
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
