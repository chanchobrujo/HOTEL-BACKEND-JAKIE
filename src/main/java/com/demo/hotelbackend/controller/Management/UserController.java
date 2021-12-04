package com.demo.hotelbackend.controller.Management;

import com.demo.hotelbackend.Model.Collections.User;
import com.demo.hotelbackend.Services.userService;
import com.demo.hotelbackend.data.DTOUser;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private userService userService;

    @GetMapping("/findById/{id}")
    public Mono<ResponseEntity<User>> findById(@PathVariable("id") String id) {
        return userService
            .findById(id)
            .map(mapper -> ResponseEntity.ok().body(mapper))
            .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/findAll")
    public Mono<ResponseEntity<Flux<User>>> findByAll() {
        return Mono.just(ResponseEntity.accepted().body(userService.findAll2()));
    }

    @PostMapping("/save")
    public Mono<ResponseEntity<Map<String, Object>>> save(
        @RequestBody @Valid DTOUser DTOuser,
        BindingResult bindinResult
    ) {
        if (bindinResult.hasErrors()) return userService.BindingResultErrors(bindinResult);

        return userService
            .save(DTOuser)
            .map(
                response -> {
                    return ResponseEntity.status(response.getStatus()).body(response.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }

    @PutMapping("/changeState/{id}")
    public Mono<ResponseEntity<Map<String, Object>>> changeState(@PathVariable("id") String id) {
        return userService
            .changeState(id)
            .map(
                response -> {
                    return ResponseEntity.status(response.getStatus()).body(response.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
