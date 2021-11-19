package com.demo.hotelbackend.controller.Management;

import com.demo.hotelbackend.Model.Collections.Guest;
import com.demo.hotelbackend.Services.guestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/guest")
public class GuestController {

    @Autowired
    private guestService service;

    @GetMapping("/findByDni/{dni}")
    public Mono<ResponseEntity<Guest>> findById(@PathVariable("dni") String dni) {
        return service
            .findByDni(dni)
            .map(mapper -> ResponseEntity.ok().body(mapper))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
