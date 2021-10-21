package com.demo.hotelbackend.controller;

import com.demo.hotelbackend.Services.userService;
import com.demo.hotelbackend.data.DTOLogin;
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin; 
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private userService service; 

    @PostMapping("/singin")
    public Mono<ResponseEntity<Map<String, Object>>> singin(@Valid @RequestBody DTOLogin DTOLogin, BindingResult bindinResult) {
        return service
            .login(DTOLogin)
            .map(
                mapper -> {
                    return ResponseEntity.status(mapper.getStatus()).body(mapper.getResponse());
                }
            )
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
