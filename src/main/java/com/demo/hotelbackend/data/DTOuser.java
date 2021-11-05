package com.demo.hotelbackend.data;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOUser {

    @NotBlank(message = "El campo dni no debe estar vacio.")
    private String dni;

    @NotBlank(message = "El campo nombre no debe estar vacio.")
    private String firtsname;

    @NotBlank(message = "El campo apellido no debe estar vacio.")
    private String lastname;

    @NotBlank(message = "El campo número no debe estar vacio.")
    private String number;

    @Email
    @NotBlank(message = "El campo email no debe estar vacio.")
    private String email;

    private Set<String> roles = new HashSet<>();
}
