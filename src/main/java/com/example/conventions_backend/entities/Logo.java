package com.example.conventions_backend.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Logo {
    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @OneToOne
    private Convention convention;
}
