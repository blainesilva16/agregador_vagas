package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.dto.FavoriteVacancyResponseDTO;
import com.devnotfound.talenthub.entity.CustomerCrawlerFavorite;
import com.devnotfound.talenthub.entity.CustomerCrawlerFavoriteId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CustomerCrawlerFavoriteRepository
        extends JpaRepository<CustomerCrawlerFavorite, CustomerCrawlerFavoriteId> {

    boolean existsByCustomerIdAndCrawlerLogId(Integer customerId, Integer crawlerLogId);

    void deleteByCustomerIdAndCrawlerLogId(Integer customerId, Integer crawlerLogId);

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
    List<FavoriteVacancyResponseDTO> findFavoriteVacanciesByCustomerId(Integer customerId);
}