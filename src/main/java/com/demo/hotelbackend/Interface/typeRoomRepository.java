package com.demo.hotelbackend.Interface;

import com.demo.hotelbackend.Model.Collections.TypeRoom;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface typeRoomRepository extends ReactiveMongoRepository<TypeRoom, String> {
    public Mono<TypeRoom> findByIdtyperoom(String idtyperoom);

    public Mono<TypeRoom> findByName(String name);

    public boolean existsByName(String name);

    public boolean existsByIdtyperoom(String idtyperoom);
}
