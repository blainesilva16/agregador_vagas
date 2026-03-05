package com.devnotfound.talenthub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "techs")
@Data
public class Tech {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    private String name;

}
