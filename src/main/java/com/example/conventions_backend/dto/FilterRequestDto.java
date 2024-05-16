package com.example.conventions_backend.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Data
@Getter
@Setter
public class FilterRequestDto {
    private String name;
    private String city;
    private String date;
    private List<String> selectedTags;
    private List<String> selectedStatusValues;
}
