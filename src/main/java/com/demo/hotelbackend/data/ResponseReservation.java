package com.demo.hotelbackend.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseReservation {

    private String date1;
    private int days;
    private Double subtotal;
    private Double tax;
    private Double total;
}
