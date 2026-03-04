package com.devnotfound.talenthub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "positions")
@Data
public class Position {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 100)
    private String name;
}

