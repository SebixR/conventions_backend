package com.example.conventions_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Schedule {
    @Id
    @GeneratedValue
    private Long id;

    @OneToOne
    private Convention convention;

    @OneToMany
    private List<Day> days;
}
