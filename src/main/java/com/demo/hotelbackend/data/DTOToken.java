package com.demo.hotelbackend.data;

import java.util.Collection;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOToken {

    private String token;
    private String bearer = "Bearer";
    private UserDetails userDetails;
    private Collection<? extends GrantedAuthority> authorities;

    public DTOToken(String token, UserDetails userDetails, Collection<? extends GrantedAuthority> authorities) {
        this.token = token;
        this.userDetails = userDetails;
        this.authorities = authorities;
    }
}
