package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Interface.userRepository;
import com.demo.hotelbackend.Model.Collections.user;
import com.demo.hotelbackend.Model.Response;
import com.demo.hotelbackend.constants.enums;
import com.demo.hotelbackend.data.DTOInsciption;
import com.demo.hotelbackend.data.DTOLogin;
import com.demo.hotelbackend.data.DTOToken;
import com.demo.hotelbackend.data.DTOUser;
import com.demo.hotelbackend.logic.Logic;
import com.demo.hotelbackend.secure.JwtProvider;
import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@Transactional
public class userService {

    @Autowired
    private userRepository repository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtProvider jwtProvider;

    public Flux<user> findAll2() {
        return Flux.fromIterable(
            repository
                .findAll()
                .toStream()
                .filter(us -> !us.getRoles().contains(enums.ROLE_ADMIN.name()))
                .collect(Collectors.toList())
        );
    }

    public Flux<user> findAll() {
        return repository.findAll();
    }

    public Mono<user> findByEmail(String email) {
        return repository.findByEmail(email);
    }

    public Mono<ResponseEntity<Map<String, Object>>> BindingResultErrors(BindingResult bindinResult) {
        Response response = new Response(
            bindinResult.getAllErrors().stream().findFirst().get().getDefaultMessage().toString(),
            null,
            HttpStatus.NOT_ACCEPTABLE
        );

        return Mono.just(ResponseEntity.internalServerError().body(response.getResponse()));
    }

    public Mono<Response> save(DTOUser DTOuser) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;
        Optional<user> userfilter = repository
            .findAll()
            .toStream()
            .filter(us -> us.getEmail().equals(DTOuser.getEmail()) || us.getNumber().equals(DTOuser.getNumber()))
            .findFirst();

        String password = Logic.generatedID();

        try {
            if (userfilter.isEmpty()) {
                Set<String> roles = new HashSet<>();
                roles.add(enums.ROLE_RECP.toString());

                user user = new user(
                    DTOuser.getFirtsname(),
                    DTOuser.getLastname(),
                    DTOuser.getNumber(),
                    DTOuser.getEmail(),
                    passwordEncoder.encode(password),
                    enums.Messages.PHOTO_NULL,
                    roles
                );
                message = Logic.sendMail(user.getEmail(), "Bienvenido, se le a asignado una contraseña:", password);
                repository.save(user).block();
            } else {
                status = HttpStatus.BAD_REQUEST;
                message = enums.Messages.REPET_DATA;
            }
        } catch (Exception e) {
            status = HttpStatus.BAD_REQUEST;
            message = enums.Messages.INCORRECT_DATA;
        }

        return Mono.just(new Response(message, null, status));
    }

    public DTOToken authorization(String email, String password) {
        try {
            Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(email, password)
            );

            SecurityContextHolder.getContext().setAuthentication(authentication);

            String jwt = jwtProvider.generateToken(authentication);
            UserDetails userDetails = (UserDetails) authentication.getPrincipal();

            return new DTOToken(jwt, userDetails, userDetails.getAuthorities());
        } catch (Exception e) {
            return null;
        }
    }

    public Mono<Response> inscription(DTOInsciption model) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = enums.Messages.INVALID_DATA;

        String password = Logic.generatedID();
        String msg = "";

        try {
            Optional<user> userfilter = repository
                .findAll()
                .toStream()
                .filter(us -> us.getEmail().equals(model.getEmail()) || us.getNumber().equals(model.getNumber()))
                .findFirst();

            if (userfilter.isEmpty()) {
                Set<String> roles = new HashSet<>();
                roles.add(enums.ROLE_HUESPED.toString());

                user customer = new user(
                    model.getFirtsname(),
                    model.getLastname(),
                    model.getNumber(),
                    model.getEmail(),
                    passwordEncoder.encode(password),
                    enums.Messages.PHOTO_NULL,
                    roles
                );

                status = HttpStatus.ACCEPTED;

                message = Logic.sendMail(model.getEmail(), "Bienvenido, se le a asignado una contraseña:", password);
                repository.save(customer).block();
            } else {
                status = HttpStatus.BAD_REQUEST;
                message = enums.Messages.REPET_DATA;
            }
        } catch (Exception e) {
            msg = "Error al enviar mensaje, verifique el correo... " + e.getMessage();
        }
        return Mono.just(new Response(message, msg, status));
    }

    public Mono<Response> login(DTOLogin login) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = enums.Messages.INVALID_DATA;

        Optional<user> user = repository
            .findAll()
            .toStream()
            .filter(use -> use.getEmail().equals(login.getUsername()))
            .findFirst();
        DTOToken jwtDto = authorization(login.getUsername(), login.getPassword());

        if (user.isPresent() && jwtDto != null) {
            status = HttpStatus.ACCEPTED;
            message = enums.Messages.VALID_DATA;
        }

        return Mono.just(new Response(message, jwtDto, status));
    }
}
