package com.it.revolution.trees.app.model.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "tree")
public class Tree {

    @Id
    private Long id;

    @Column(name = "register_number", nullable = false)
    private String registrationNumber;

    @Column(name = "x", nullable = false)
    private Double x;

    @Column(name = "y", nullable = false)
    private Double y;

    @Column(name = "radius", nullable = false)
    private Integer radius;

    @Column(name = "birth_date", nullable = false)
    private LocalDateTime birthDate;

    @ManyToOne
    private TreeType type;

    @Enumerated(EnumType.ORDINAL)
    private TreeState state;

    @Column(name = "photo_url")
    private String photoUrl;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "tree", cascade = CascadeType.ALL)
    private List<AssignedTreeTask> tasks;

}
