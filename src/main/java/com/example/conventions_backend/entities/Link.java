package com.example.conventions_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Link {
    @Id
    @GeneratedValue
    private Long id;

    private String link;

    @ManyToOne
    private Convention convention;
}
