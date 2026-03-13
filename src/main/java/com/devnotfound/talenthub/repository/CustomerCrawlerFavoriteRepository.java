package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.dto.FavoriteVacancyResponseDTO;
import com.devnotfound.talenthub.entity.CustomerCrawlerFavorite;
import com.devnotfound.talenthub.entity.CustomerCrawlerFavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CustomerCrawlerFavoriteRepository
        extends JpaRepository<CustomerCrawlerFavorite, CustomerCrawlerFavoriteId> {

    boolean existsByCustomerIdAndCrawlerLogId(Long customerId, Long crawlerLogId);

    void deleteByCustomerIdAndCrawlerLogId(Long customerId, Long crawlerLogId);

    @Query("""
            SELECT new com.devnotfound.talenthub.dto.FavoriteVacancyResponseDTO(
                f.crawlerLog.id,
                f.crawlerLog.title,
                f.crawlerLog.companyName,
                f.crawlerLog.postingLink,
                f.creationDate
            )
            FROM CustomerCrawlerFavorite f
            WHERE f.customer.id = :customerId
            ORDER BY f.creationDate DESC
            """)
    List<FavoriteVacancyResponseDTO> findFavoriteVacanciesByCustomerId(Long customerId);
}