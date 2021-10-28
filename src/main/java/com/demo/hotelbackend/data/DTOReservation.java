package com.demo.hotelbackend.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOReservation {

    private String id;

    @NotBlank(message = "El campo dni del huesped no debe estar vacio.")
    private String dni;

    @NotBlank(message = "El campo nombre del huesped no debe estar vacio.")
    private String firtsname;

    @NotBlank(message = "El campo apellido del huesped no debe estar vacio.")
    private String lastname;

    @Email
    @NotBlank(message = "El campo email del huesped no debe estar vacio.")
    private String email;

    @NotBlank(message = "El campo número telefónico del huesped no debe estar vacio.")
    private String phone;

    @NotBlank(message = "El campo fecha de inicio de reservación no debe estar vacio.")
    private String date_ini;

    @NotBlank(message = "El campo fecha de fin de reservación no debe estar vacio.")
    private String date_end;

    @NotBlank(message = "El campo id de reservación no debe estar vacio.")
    private String idroom;

    @NotBlank(message = "El campo id de usuario no debe estar vacio.")
    private String iduser;

    private Double subtotal;

    private Double tax;

    private Double total;

    private String requirements;
}
