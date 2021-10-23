package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Interface.typeRoomRepository;
import com.demo.hotelbackend.Model.Collections.TypeRoom;
import com.demo.hotelbackend.Model.Response;
import com.demo.hotelbackend.constants.enums;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class typeRoomService {

    @Autowired
    private typeRoomRepository repository;

    public Flux<TypeRoom> findAll() {
        return repository.findAll();
    }

    public Mono<TypeRoom> findByIdtyperoom(String idtyperoom) {
        return repository.findByIdtyperoom(idtyperoom);
    }

    public Mono<TypeRoom> findByName(String name) {
        return repository.findByName(name);
    }

    public Mono<Response> save(TypeRoom typeroom) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;

        repository.save(typeroom).subscribe();

        return Mono.just(new Response(message, typeroom, status));
    }
}
