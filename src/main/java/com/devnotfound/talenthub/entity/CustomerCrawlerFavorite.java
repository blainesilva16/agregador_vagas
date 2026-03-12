package com.devnotfound.talenthub.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "customerscrawlers")
@Data
@NoArgsConstructor
public class CustomerCrawlerFavorite {

    @EmbeddedId
    private CustomerCrawlerFavoriteId id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("customerId")
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @MapsId("crawlerId")
    @JoinColumn(name = "crawler_id", nullable = false)
    private CrawlerLog crawlerLog;

    @CreationTimestamp
    @Column(name = "creation_date", updatable = false)
    private LocalDateTime creationDate;
}