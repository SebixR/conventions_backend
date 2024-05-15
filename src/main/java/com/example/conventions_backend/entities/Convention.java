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
    private String logo;
    @Enumerated(EnumType.STRING)
    private ConventionStatus conventionStatus;

    @ManyToOne
    private AppUser user;
    @OneToOne(mappedBy = "convention", cascade = CascadeType.ALL)
    private Address address;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "convention_tag",
            joinColumns = @JoinColumn(name = "convention_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )
    private List<Tag> tags;
}
