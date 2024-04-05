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
public class Day {
    @Id
    @GeneratedValue
    private Long id;

    private LocalDate date;

    @ManyToOne
    private Schedule schedule;

    @OneToMany
    private List<Event> events;
}
