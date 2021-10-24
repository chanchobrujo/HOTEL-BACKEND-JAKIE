package com.demo.hotelbackend.data;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DTOuser {

    private String firtsname;
    private String lastname;
    private String number;
    private String email;
    private Set<String> roles = new HashSet<>();
}
