package com.demo.hotelbackend.data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOLogin {

    @Email
    @NotBlank(message = "El campo nombre no debe estar vacio.")
    private String username;

    @NotBlank(message = "El campo nombre no debe estar vacio.")
    private String password;
}
