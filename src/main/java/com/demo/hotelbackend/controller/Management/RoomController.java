package com.demo.hotelbackend.controller.Management;

import com.demo.hotelbackend.Model.Collections.Room;
import com.demo.hotelbackend.Services.roomService;
import com.demo.hotelbackend.data.DTORoom;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping("/room")
public class RoomController {

    @Autowired
    private roomService service;

    @GetMapping("/")
    public Mono<ResponseEntity<Flux<Room>>> findByAll() {
        return Mono.just(ResponseEntity.accepted().body(service.findAll()));
    }

    @GetMapping("/findByType/{type}")
    public Mono<ResponseEntity<Flux<Room>>> findByAll(@PathVariable("type") String type) {
        return Mono.just(ResponseEntity.accepted().body(service.findByIdtype(type)));
    }

    @GetMapping("/findById/{idroomm}")
    public Mono<ResponseEntity<Room>> findById(@PathVariable("idroomm") String idroomm) {
        return service
            .findByIdroomm(idroomm)
            .map(mapper -> ResponseEntity.ok().body(mapper))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findByName/{name}")
    public Mono<ResponseEntity<Room>> findByName(@PathVariable("name") String name) {
        return service
            .findByName(name)
            .map(mapper -> ResponseEntity.ok().body(mapper))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Map<String, Object>>> save(
        @RequestBody @Valid DTORoom DTORoom,
        BindingResult bindinResult
    ) {
        if (bindinResult.hasErrors()) return service.BindingResultErrors(bindinResult);

        return service
            .save(DTORoom)
            .map(response -> {
                return ResponseEntity.status(response.getStatus()).body(response.getResponse());
            })
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @DeleteMapping("/deleteById/{idroomm}")
    public Mono<ResponseEntity<Map<String, Object>>> daleteById(@PathVariable("idroomm") String idroomm) {
        return service
            .delete(idroomm)
            .map(response -> {
                return ResponseEntity.status(response.getStatus()).body(response.getResponse());
            })
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @PutMapping("/changeState/{idroomm}")
    public Mono<ResponseEntity<Map<String, Object>>> changeState(@PathVariable("idroomm") String idroomm) {
        return service
            .changeState(idroomm)
            .map(response -> {
                return ResponseEntity.status(response.getStatus()).body(response.getResponse());
            })
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
