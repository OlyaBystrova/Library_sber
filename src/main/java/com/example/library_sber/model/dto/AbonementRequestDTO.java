package com.example.library_sber.model.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class AbonementRequestDTO{
    String fullName;
    Date openDate;
    private List<String> roles = new ArrayList<>();
}
