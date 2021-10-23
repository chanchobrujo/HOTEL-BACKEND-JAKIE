package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Interface.userRepository;
import com.demo.hotelbackend.Model.Response;
import com.demo.hotelbackend.Model.user;
import com.demo.hotelbackend.constants.enums;
import com.demo.hotelbackend.data.DTOLogin;
import com.demo.hotelbackend.data.DTOToken;
import com.demo.hotelbackend.secure.JwtProvider;
import java.util.Optional;
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

    public Optional<user> findByEmail(String email) {
        return findAll().toStream().filter(us -> us.getEmail().equals(email)).findFirst();
    }

    public Optional<String> findByRole(user user, String rol) {
        return user.getRoles().stream().filter(role -> role.equals(rol)).findFirst();
    }

    public Mono<Response> save(user user) {
        HttpStatus status = HttpStatus.ACCEPTED;
        String message = enums.Messages.CORRECT_DATA;

        user usersave = repository.save(user).block();

        return Mono.just(new Response(message, usersave, status));
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

    public Mono<Response> login(DTOLogin login, String prerole) {
        HttpStatus status = HttpStatus.UNAUTHORIZED;
        String message = enums.Messages.INVALID_DATA; 

        Optional<user> user = findByEmail(login.getUsername());
        DTOToken jwtDto = authorization(login.getUsername(), login.getPassword());

        if (user.isPresent() && findByRole(user.get(), prerole).isPresent() && jwtDto != null) {
            status = HttpStatus.ACCEPTED;
            message = enums.Messages.VALID_DATA; 
        }

        return Mono.just(new Response(message, jwtDto, status));
    }
}
