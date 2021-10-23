package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Interface.RoomRepository;
import com.demo.hotelbackend.Interface.typeRoomRepository;
import com.demo.hotelbackend.Model.Collections.Room;
import com.demo.hotelbackend.Model.Response;
import com.demo.hotelbackend.constants.enums;
import com.demo.hotelbackend.data.DTORoom;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class roomService {

    @Autowired
    private RoomRepository roomrepository;

    @Autowired
    private typeRoomRepository typeRoomRepository;

    public Mono<ResponseEntity<Map<String, Object>>> BindingResultErrors(BindingResult bindinResult) {
        Response response = new Response(
            bindinResult.getAllErrors().stream().findFirst().get().getDefaultMessage().toString(),
            null,
            HttpStatus.NOT_ACCEPTABLE
        );

        return Mono.just(ResponseEntity.internalServerError().body(response.getResponse()));
    }

    public Flux<Room> findAll() {
        return roomrepository.findAll();
    }

    public Mono<Room> findByIdroomm(String idroomm) {
        return roomrepository.findByIdroomm(idroomm);
    }

    public Mono<Room> findByName(String name) {
        return roomrepository.findByName(name);
    }

    public Flux<Room> findByIdtype(String idtype) {
        return roomrepository.findByIdtype(idtype);
    }

    public Mono<Response> save(DTORoom DTORoom) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = enums.Messages.INCORRECT_DATA;
        Room room = null;

        if (typeRoomRepository.existsById(DTORoom.getIdtype()).block()) {
            status = HttpStatus.ACCEPTED;
            message = enums.Messages.CORRECT_DATA;

            room = new Room(DTORoom.getName(), DTORoom.getDescription(), DTORoom.getIdtype(), DTORoom.getPrice());

            roomrepository.save(room).subscribe();
        }

        return Mono.just(new Response(message, room, status));
    }
}
