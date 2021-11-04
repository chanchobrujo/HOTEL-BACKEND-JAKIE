package com.demo.hotelbackend.data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTORoom {

    private String idroom = "";

    @Min(0)
    @Max(5)
    private int flat;

    @NotBlank(message = "El campo descripción no debe estar vacio.")
    private String description;

    @Min(50)
    private Double price;

    @NotBlank(message = "El campo tipo de habitación no debe estar vacio.")
    private String idtype;

    @NotBlank(message = "El campo photo no debe estar vacio.")
    private String photo;
}
