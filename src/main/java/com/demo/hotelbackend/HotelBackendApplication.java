package com.demo.hotelbackend;

import com.demo.hotelbackend.Services.userdetailsServiceI;
import com.demo.hotelbackend.secure.JwtEntryPoint;
import com.demo.hotelbackend.secure.JwtTokenFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Slf4j
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@SpringBootApplication
public class HotelBackendApplication extends WebSecurityConfigurerAdapter {

    @Autowired
    userdetailsServiceI userdetailsServiceI;

    @Autowired
    JwtEntryPoint jwtEntryPoint;

    @Bean
    public JwtTokenFilter jwtTokenFilter() {
        return new JwtTokenFilter();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userdetailsServiceI).passwordEncoder(passwordEncoder());
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected AuthenticationManager authenticationManager() throws Exception {
        return super.authenticationManager();
    }

    @Override
    protected void configure(HttpSecurity http) throws AccessDeniedException {
        try {
            http.authorizeRequests().antMatchers("/auth/**").permitAll();

            http.authorizeRequests().antMatchers(HttpMethod.GET, "/room/").permitAll();
            http.authorizeRequests().antMatchers(HttpMethod.GET, "/room/findById/{idroomm}").permitAll();
            http.authorizeRequests().antMatchers(HttpMethod.POST, "/room/save").hasRole("ADMIN");
            http
                .authorizeRequests()
                .antMatchers(HttpMethod.PUT, "/room/changeState/{idroomm}")
                .hasAnyRole("ADMIN", "RECP");
            http.authorizeRequests().antMatchers(HttpMethod.DELETE, "/room/deleteById/{idroomm}").hasRole("ADMIN");

            http
                .cors()
                .and()
                .csrf()
                .disable()
                .authorizeRequests()
                .anyRequest()
                .authenticated()
                .and()
                .exceptionHandling()
                .authenticationEntryPoint(jwtEntryPoint)
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
            http.addFilterBefore(jwtTokenFilter(), UsernamePasswordAuthenticationFilter.class);
        } catch (Exception e) {
            log.info(e.getMessage());
        }
    }

    public static void main(String[] args) {
        SpringApplication.run(HotelBackendApplication.class, args);
        log.info("Proyecto inicializado.");
    }
}
