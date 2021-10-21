package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Interface.userRepository;
import com.demo.hotelbackend.Model.Response;
import com.demo.hotelbackend.Model.user;
import com.demo.hotelbackend.constants.enums;
import com.demo.hotelbackend.data.DTOLogin;
import com.demo.hotelbackend.data.DTOToken;
import com.demo.hotelbackend.secure.JwtProvider;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class userService {

    @Autowired
    private userRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    //@Autowired
    //private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public Flux<user> findAll() {
        return repository.findAll();
    }

    public Mono<Response> save(user user) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;

        user usersave = repository.save(user).block();

        return Mono.just(new Response(message, usersave, status));
    }

    public Mono<Response> login(DTOLogin login) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = enums.Messages.INVALID_DATA;
        DTOToken jwtDto = null;

        if (
            !findAll()
                .toStream()
                .filter(u -> u.getEmail().equals(login.getUsername()))
                .collect(Collectors.toList())
                .isEmpty()
        ) {
            status = HttpStatus.ACCEPTED;
            message = enums.Messages.VALID_DATA;

            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword())
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            jwtDto = new DTOToken(jwt, userDetails, userDetails.getAuthorities());
        }

        return Mono.just(new Response(message, jwtDto, status));
    }
}
