package com.devnotfound.talenthub.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "logsearch")
@Data
public class LogSearch {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "item_search")
    private String itemSearch;

    @Column(name = "date_search")
    private LocalDateTime dateSearch;
}
