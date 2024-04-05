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
    @Enumerated(EnumType.STRING)
    private ConventionStatus conventionStatus;

    @ManyToOne
    private AppUser user;

    @OneToOne
    private Schedule schedule;

    @OneToOne
    private Logo logo;

    @OneToMany
    private List<Address> addresses;

    @OneToMany
    private List<TicketPrice> ticketPrices;

    @OneToMany
    private List<Link> links;

    @OneToMany
    private List<Tag> tags;

    @OneToMany
    private List<Photo> photos;
}
