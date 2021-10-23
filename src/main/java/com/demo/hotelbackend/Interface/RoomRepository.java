package com.demo.hotelbackend.Interface;

import com.demo.hotelbackend.Model.Collections.Room;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface RoomRepository extends ReactiveMongoRepository<Room, String> {
    public Mono<Room> findByIdroomm(String idroomm);

    public Mono<Room> findByName(String name);

    public Flux<Room> findByIdtype(String idtype);

    public boolean existsByIdroomm(String idroomm);

    public boolean existsByName(String name);
}
