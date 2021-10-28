package com.demo.hotelbackend.Model.Collections;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "guest")
public class Guest {

    @Id
    private String idguest;

    private String dni;
    private String firtsname;
    private String lastname;
    private String email;
    private String phone;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime datecreated = LocalDateTime.now(ZoneId.of("America/Lima"));

    public Guest(String dni, String firtsname, String lastname, String email, String phone) {
        this.dni = dni;
        this.firtsname = firtsname;
        this.lastname = lastname;
        this.email = email;
        this.phone = phone;
    }
}
