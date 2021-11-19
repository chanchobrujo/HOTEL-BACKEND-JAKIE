package com.demo.hotelbackend.Interface;

import com.demo.hotelbackend.Model.Collections.Guest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface GuestRepository extends ReactiveMongoRepository<Guest, String> {
    public Mono<Guest> findByDni(String dni);
}
