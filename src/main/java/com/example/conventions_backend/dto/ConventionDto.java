package com.example.conventions_backend.dto;

import com.example.conventions_backend.entities.AppUser;
import com.example.conventions_backend.entities.Convention;
import com.example.conventions_backend.entities.ConventionStatus;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Data
@Setter
@Getter
public class ConventionDto {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String logo;
    @Enumerated(EnumType.STRING)
    private ConventionStatus conventionStatus;

    public static ConventionDto fromConvention(Convention convention) {
        ConventionDto conventionDto = new ConventionDto();
        conventionDto.setName(convention.getName());
        conventionDto.setStartDate(convention.getStartDate());
        conventionDto.setEndDate(convention.getEndDate());
        conventionDto.setDescription(convention.getDescription());
        conventionDto.setLogo(convention.getLogo());
        conventionDto.setConventionStatus(convention.getConventionStatus());

        return conventionDto;
    }
}
