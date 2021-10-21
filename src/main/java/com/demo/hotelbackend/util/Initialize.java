package com.demo.hotelbackend.util;

import com.demo.hotelbackend.Model.user;
import com.demo.hotelbackend.Services.userService;
import com.demo.hotelbackend.constants.enums;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initialize implements CommandLineRunner {

    @Autowired
    userService service;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (service.findAll().toStream().collect(Collectors.toList()).isEmpty()) {
            Set<String> roles = new HashSet<>();
            roles.add(enums.ADMIN.name());

            user useradmin = new user(
                "KEVIN ANDERSON",
                "PALMA LLUÉN",
                "947275237",
                "umb.kevsidorov@gmail.com",
                passwordEncoder.encode("123456"),
                roles
            );
            service.save(useradmin);
        }
    }
}
