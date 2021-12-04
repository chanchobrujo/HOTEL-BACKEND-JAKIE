package com.demo.hotelbackend.Model;

import com.demo.hotelbackend.Model.Collections.User;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Data
public class userprincipal implements UserDetails {

    private String email;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public userprincipal(
        String number,
        String email,
        String password,
        Collection<? extends GrantedAuthority> authorities
    ) {
        this.authorities = authorities;
        this.email = email;
        this.password = password;
    }

    public static userprincipal build(User user) {
        List<GrantedAuthority> authorities = user
            .getRoles()
            .stream()
            .map(rol -> new SimpleGrantedAuthority(rol))
            .collect(Collectors.toList());

        return new userprincipal(user.getNumber(), user.getEmail(), user.getPassword(), authorities);
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
