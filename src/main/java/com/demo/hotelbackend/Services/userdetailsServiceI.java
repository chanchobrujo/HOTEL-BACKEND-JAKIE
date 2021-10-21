package com.demo.hotelbackend.Services;

import com.demo.hotelbackend.Model.userprincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class userdetailsServiceI implements UserDetailsService {

    @Autowired
    userService service;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userprincipal.build(
            service
                .findAll()
                .toStream()
                .filter(user -> user.getEmail().equalsIgnoreCase(username))
                .findFirst()
                .get()
        );
    }
}
