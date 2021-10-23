package com.demo.hotelbackend.Model;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "user")
public class user {

    @Id
    private String idaccount;

    private String firtsname;
    private String lastname;
    private String number;
    private String email;
    private String password;
    private Boolean state;
    private Set<String> roles = new HashSet<>();

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime datecreated = LocalDateTime.now(ZoneId.of("America/Lima"));

    public user(String firtsname, String lastname, String number, String email, String password, Set<String> roles) {
        this.firtsname = firtsname;
        this.lastname = lastname;
        this.number = number;
        this.roles = roles;
        this.email = email;
        this.password = password;
    }
}
