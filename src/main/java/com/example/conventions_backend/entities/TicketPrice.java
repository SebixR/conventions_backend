package com.example.conventions_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class TicketPrice {
    @Id
    @GeneratedValue
    private Long id;

    private double price;
    @Column(nullable = true)
    private String description;

    @ManyToOne
    private Convention convention;
}
