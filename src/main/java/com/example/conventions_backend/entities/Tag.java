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
public class Tag {
    @Id
    @GeneratedValue
    private Long id;

    private String tag;

    @ManyToMany(mappedBy = "tags")
    private List<Convention> conventions;
}
