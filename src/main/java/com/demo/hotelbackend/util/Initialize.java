package com.demo.hotelbackend.util;

import com.demo.hotelbackend.Model.Collections.TypeRoom;
import com.demo.hotelbackend.Model.Collections.user;
import com.demo.hotelbackend.Services.typeRoomService;
import com.demo.hotelbackend.Services.userService;
import com.demo.hotelbackend.constants.enums;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initialize implements CommandLineRunner {

    @Autowired
    private userService userService;

    @Autowired
    private typeRoomService typeRoomService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) throws Exception {
        if (typeRoomService.findAll().toStream().count() == 0) {
            typeRoomService.save(new TypeRoom("DOBLE"));
            typeRoomService.save(new TypeRoom("SIMPLE"));
            typeRoomService.save(new TypeRoom("FAMILIAR"));
            typeRoomService.save(new TypeRoom("MATRIMONIAL"));
        }

        if (userService.findAll().toStream().count() == 0) {
            Set<String> roles = new HashSet<>();
            roles.add(enums.ROLE_ADMIN.name());

            user useradmin = new user(
                "Jackeline",
                "Picoy Rosas",
                "941472816",
                "Jpicoyrosas@gmail.com",
                passwordEncoder.encode("123456"),
                roles
            );
            useradmin.setState(true);
            userService.save(useradmin);
        }
    }
}
