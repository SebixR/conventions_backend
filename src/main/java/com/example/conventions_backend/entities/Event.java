package com.example.conventions_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Event {
    @Id
    @GeneratedValue
    private Long id;

    private String name;
    private LocalTime startTime;
    private LocalTime endTime;

    @ManyToOne
    private Day day;
}
