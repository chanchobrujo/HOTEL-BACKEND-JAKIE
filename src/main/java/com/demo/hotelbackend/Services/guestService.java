package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Interface.GuestRepository;
import com.demo.hotelbackend.Model.Collections.Guest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

@Service
public class guestService {

    @Autowired
    private GuestRepository repository;

    public Mono<Guest> findByDni(String dni) {
        return repository.findByDni(dni);
    }
}
