package com.demo.hotelbackend.Interface;

import com.demo.hotelbackend.Model.user;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface userRepository extends ReactiveMongoRepository<user, String> {
    public Mono<user> findByIdaccount(String idaccount);

    public Mono<user> findByEmail(String email);

    public Mono<user> findByNumber(String number);

    public boolean existsByEmail(String email);

    public boolean existsByNumber(String number);
}
