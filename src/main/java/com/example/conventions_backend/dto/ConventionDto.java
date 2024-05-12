package com.example.conventions_backend.dto;

import com.example.conventions_backend.entities.*;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Data
@Setter
@Getter
public class ConventionDto {
    private Long userId;
    private String eventName;
    private String logo;
    private String selectedStartDate;
    private String selectedEndDate;
    private String city;
    private String country;
    private String address1;
    private String address2;
    private List<TicketPrice> tickets;
    private List<Link> links;
    private String description;
    private List<Tag> tags;
    private List<Photo> photos;
    @Enumerated(EnumType.STRING)
    private ConventionStatus conventionStatus;

    public static ConventionDto fromConvention(Convention convention) {
        ConventionDto conventionDto = new ConventionDto();
        conventionDto.setUserId(convention.getUser().getId());
        conventionDto.setEventName(convention.getName());
        conventionDto.setLogo(convention.getLogo());
        conventionDto.setSelectedStartDate(convention.getStartDate().toString());
        conventionDto.setSelectedEndDate(convention.getEndDate().toString());
        conventionDto.setCity(convention.getAddress().getCity());
        conventionDto.setCountry(convention.getAddress().getCountry());
        conventionDto.setAddress1(convention.getAddress().getAddress1());
        conventionDto.setAddress2(convention.getAddress().getAddress2());
        conventionDto.setTickets(convention.getTicketPrices());
        conventionDto.setLinks(convention.getLinks());
        conventionDto.setDescription(convention.getDescription());
        conventionDto.setTags(convention.getTags());
        conventionDto.setPhotos(convention.getPhotos());

        int startDateComparison = LocalDate.parse(conventionDto.getSelectedStartDate()).compareTo(LocalDate.now());
        int endDateComparison = LocalDate.parse(conventionDto.getSelectedEndDate()).compareTo(LocalDate.now());
        if ((startDateComparison == 0 || startDateComparison < 0) //start date is today or passed
                && (endDateComparison == 0 || endDateComparison > 0)) { //end date is today or upcoming
            conventionDto.setConventionStatus(ConventionStatus.ONGOING);
        }
        else if (startDateComparison > 0) { //start date is upcoming
            conventionDto.setConventionStatus(ConventionStatus.UPCOMING);
        }
        else {
            conventionDto.setConventionStatus(ConventionStatus.OVER);
        }

        return conventionDto;
    }
}
