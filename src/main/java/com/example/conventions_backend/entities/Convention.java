package com.example.conventions_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Convention {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String logo;
    @Enumerated(EnumType.STRING)
    private ConventionStatus conventionStatus;

    @ManyToOne
    private AppUser user;
    @OneToMany(mappedBy = "convention")
    private List<Day> days;
    @OneToMany(mappedBy = "convention")
    private List<Address> addresses;
    @OneToMany(mappedBy = "convention")
    private List<TicketPrice> ticketPrices;
    @OneToMany(mappedBy = "convention")
    private List<Link> links;
    @OneToMany(mappedBy = "convention")
    private List<Tag> tags;
    @OneToMany(mappedBy = "convention")
    private List<Photo> photos;
}
