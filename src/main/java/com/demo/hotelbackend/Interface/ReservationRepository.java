package com.demo.hotelbackend.Interface;

import com.demo.hotelbackend.Model.Collections.Reservation;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;

public interface ReservationRepository extends ReactiveMongoRepository<Reservation, String> {}
