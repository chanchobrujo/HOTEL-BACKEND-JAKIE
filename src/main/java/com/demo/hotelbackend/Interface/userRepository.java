package com.demo.hotelbackend.Interface;

import com.demo.hotelbackend.Model.Collections.User;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Mono;

public interface userRepository extends ReactiveMongoRepository<User, String> {
    public Mono<User> findByIdaccount(String idaccount);

    public Mono<User> findByEmail(String email);

    public Mono<User> findByNumber(String number);

    public boolean existsByEmail(String email);

    public boolean existsByNumber(String number);
}
