package com.demo.hotelbackend.util;

import com.demo.hotelbackend.Interface.typeRoomRepository;
import com.demo.hotelbackend.Interface.userRepository;
import com.demo.hotelbackend.Model.Collections.TypeRoom;
import com.demo.hotelbackend.Model.Collections.user;
import com.demo.hotelbackend.constants.enums;
import java.util.HashSet;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class Initialize implements CommandLineRunner {

    @Autowired
    private userRepository userRepository;

    @Autowired
    private typeRoomRepository typeRoomRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Value("${password}")
    private String password;

    @Override
    public void run(String... args) throws Exception {
        if (typeRoomRepository.findAll().toStream().count() == 0) {
            typeRoomRepository.save(new TypeRoom("DOBLE")).subscribe();
            typeRoomRepository.save(new TypeRoom("SIMPLE")).subscribe();
            typeRoomRepository.save(new TypeRoom("FAMILIAR")).subscribe();
            typeRoomRepository.save(new TypeRoom("MATRIMONIAL")).subscribe();
        }
        System.out.print(" GAAAAAAAAAA " + password);
        if (userRepository.findAll().count().block() == 0) {
            Set<String> roles = new HashSet<>();
            roles.add(enums.ROLE_ADMIN.name());

            user useradmin = new user(
                "Jackeline",
                "Picoy Rosas",
                "941472816",
                "Jpicoyrosas@gmail.com",
                passwordEncoder.encode(password),
                enums.Messages.PHOTO_NULL,
                roles
            );
            useradmin.setState(true);
            userRepository.save(useradmin).subscribe();
        }
    }
}
