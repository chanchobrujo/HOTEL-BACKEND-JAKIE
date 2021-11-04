package com.demo.hotelbackend.controller.Profile;

import com.demo.hotelbackend.Model.Collections.user;
import com.demo.hotelbackend.Services.userService;
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
@RequestMapping("/profile")
public class ProfileController {

    @Autowired
    private userService service;

    @GetMapping("/findByEmail/{email}")
    public Mono<ResponseEntity<user>> findByName(@PathVariable("email") String email) {
        return service
            .findByEmail(email)
            .map(mapper -> {
                mapper.setPassword(null);
                return ResponseEntity.ok().body(mapper);
            })
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
