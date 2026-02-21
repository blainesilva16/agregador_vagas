package com.devnotfound.talenthub.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "token")
@Data
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 250)
    private String token;

}
