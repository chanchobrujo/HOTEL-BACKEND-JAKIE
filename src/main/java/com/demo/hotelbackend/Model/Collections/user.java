package com.demo.hotelbackend.Model.Collections;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "user")
public class User {

    @Id
    private String idaccount;

    private String dni;
    private String firtsname;
    private String lastname;
    private String number;
    private String email;
    private String password;
    private String photo;
    private Boolean state;
    private Set<String> roles = new HashSet<>();

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime datecreated = LocalDateTime.now(ZoneId.of("America/Lima"));

    public User(
        String dni,
        String firtsname,
        String lastname,
        String number,
        String email,
        String password,
        String photo,
        Set<String> roles
    ) {
        this.dni = dni;
        this.firtsname = firtsname;
        this.lastname = lastname;
        this.number = number;
        this.roles = roles;
        this.email = email;
        this.photo = photo;
        this.password = password;
        this.state = true;
    }
}
