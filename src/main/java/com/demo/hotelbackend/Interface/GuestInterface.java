package com.demo.hotelbackend.Interface;

import com.demo.hotelbackend.Model.Collections.Guest;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface GuestInterface extends ReactiveMongoRepository<Guest, String> {}
