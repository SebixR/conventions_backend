package com.example.conventions_backend.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Address {
    @Id
    @GeneratedValue
    private Long id;

    private String country;
    private String city;
    private String address1;
    @Column(nullable = true)
    private String address2;

    @OneToOne
    private Convention convention;
}
