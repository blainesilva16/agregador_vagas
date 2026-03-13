package com.devnotfound.talenthub.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
public class CustomerCrawlerFavoriteId implements Serializable {

    private static final long serialVersionUID = 1L;

    @Column(name = "customer_id")
    private Integer customerId;

    @Column(name = "crawler_id")
    private Integer crawlerId;

    public CustomerCrawlerFavoriteId(Integer customerId, Integer crawlerId) {
        this.customerId = customerId;
        this.crawlerId = crawlerId;
    }
}
