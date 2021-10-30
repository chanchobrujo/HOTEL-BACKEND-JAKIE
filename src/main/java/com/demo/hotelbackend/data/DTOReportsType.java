package com.demo.hotelbackend.data;

import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DTOReportsType {

    private List<String> types;
    private List<Integer> count;

    public DTOReportsType() {
        this.types = new ArrayList<>();
        this.count = new ArrayList<>();
    }
}
