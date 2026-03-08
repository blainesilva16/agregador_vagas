package com.devnotfound.talenthub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "crawlerlogs")
@Data
@NoArgsConstructor
public class CrawlerLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String title;

    @Column(name = "company_name", nullable = false, length = 100)
    private String companyName;

    @Column(name = "posting_link", nullable = false, length = 250)
    private String postingLink;
}