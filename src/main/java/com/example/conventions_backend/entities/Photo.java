package com.example.conventions_backend.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Photo {
    @Id
    @GeneratedValue
    private Long id;

    private String fileName;

    @ManyToOne
    @JsonIgnore
    private Convention convention;
}
