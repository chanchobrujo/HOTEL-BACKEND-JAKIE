package com.demo.hotelbackend.Model.Collections;

import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@Document(collection = "reservation")
public class Reservation {

    @Id
    private String idreservation;

    private Date date_ini;
    private Date date_end;

    private String requirements;
    private Guest guest;
    private Boolean state;

    @JsonFormat(pattern = "dd-MM-yyyy HH:mm:ss")
    private LocalDateTime datecreated = LocalDateTime.now(ZoneId.of("America/Lima"));

    public Reservation(Date date_ini, Date date_end, String requirements, Guest guest) {
        this.date_ini = date_ini;
        this.date_end = date_end;
        this.requirements = requirements;
        this.guest = guest;
        this.state = true;
    }
}
