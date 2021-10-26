package com.demo.hotelbackend.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOInsciption {

    @NotBlank(message = "El campo nombre no debe estar vacio.")
    private String firtsname;

    @NotBlank(message = "El campo apellido no debe estar vacio.")
    private String lastname;

    @NotBlank(message = "El campo número no debe estar vacio.")
    private String number;

    @Email
    @NotBlank(message = "El campo email no debe estar vacio.")
    private String email;
}
