package com.demo.hotelbackend.controller.Management;

import com.demo.hotelbackend.Model.Collections.TypeRoom;
import com.demo.hotelbackend.Services.typeRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/typeroom")
public class TypeRoomController {

    @Autowired
    private typeRoomService service;

    @GetMapping("/")
    public Mono<ResponseEntity<Flux<TypeRoom>>> findByAll() {
        return Mono.just(ResponseEntity.accepted().body(service.findAll()));
    }

    @GetMapping("/{idtyperoom}")
    public Mono<ResponseEntity<TypeRoom>> findById(@PathVariable("idtyperoom") String idtyperoom) {
        return service
            .findByIdtyperoom(idtyperoom)
            .map(mapper -> ResponseEntity.ok().body(mapper))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
