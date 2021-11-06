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

    private String defineName(int flat) {
        String name = "";
        int count = (int) roomrepository.findAll().toStream().filter(room -> room.getFlat() == flat).count();
        if (count > 9) {
            name = flat + "" + count;
        } else {
            name = flat + "0" + count;
        }
        return name;
    }

    public Flux<Room> findAll() {
        return roomrepository.findAll();
    }

    public Mono<Room> findByIdroomm(String idroomm) {
        return roomrepository.findById(idroomm);
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

        String id = DTORoom.getIdroom() == null ? "" : DTORoom.getIdroom();
        DTORoom.setIdroom(id);

        if (typeRoomRepository.existsById(DTORoom.getIdtype()).block()) {
            room = new Room(DTORoom.getIdtype(), DTORoom.getPrice(), DTORoom.getPhoto(), DTORoom.getChildren());

            if (roomrepository.existsById(DTORoom.getIdroom()).block()) {
                room.setIdroomm(DTORoom.getIdroom());
            } else {
                room.setFlat(DTORoom.getFlat());
                room.setName(defineName(DTORoom.getFlat()));
            }

            status = HttpStatus.ACCEPTED;
            message = enums.Messages.CORRECT_DATA;
            roomrepository.save(room).subscribe();
        }

        return Mono.just(new Response(message, room, status));
    }

    public Mono<Response> delete(String id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = enums.Messages.INCORRECT_DATA;

        if (roomrepository.existsById(id).block()) {
            status = HttpStatus.ACCEPTED;
            message = enums.Messages.DELETE_DATA;

            roomrepository.deleteById(id).subscribe();
        }

        return Mono.just(new Response(message, null, status));
    }

    public Mono<Response> changeState(String id) {
        HttpStatus status = HttpStatus.NOT_FOUND;
        String message = enums.Messages.INCORRECT_DATA;

        if (roomrepository.existsById(id).block()) {
            Room room = findByIdroomm(id).block();
            Boolean state = room.getState();
            room.setState(!state);

            status = HttpStatus.ACCEPTED;
            message = enums.Messages.CORRECT_DATA;

            roomrepository.save(room).subscribe();
        }

        return Mono.just(new Response(message, null, status));
    }
}
