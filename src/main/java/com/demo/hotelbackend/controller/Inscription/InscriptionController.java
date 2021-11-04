package com.demo.hotelbackend.controller.Inscription;

import com.demo.hotelbackend.Services.userService;
import com.demo.hotelbackend.data.DTOInsciption;
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
@RequestMapping("/inscription")
public class InscriptionController {

    @Autowired
    private userService service;

    @PostMapping("/")
    public Mono<ResponseEntity<Map<String, Object>>> inscription(
        @Valid @RequestBody DTOInsciption DTOLogin,
        BindingResult bindinResult
    ) {
        if (bindinResult.hasErrors()) return service.BindingResultErrors(bindinResult);

        return service
            .inscription(DTOLogin)
            .map(mapper -> {
                return ResponseEntity.status(mapper.getStatus()).body(mapper.getResponse());
            })
            .defaultIfEmpty(ResponseEntity.internalServerError().build());
    }
}
