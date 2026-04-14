package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.FavoriteVacancyResponseDTO;
import com.devnotfound.talenthub.dto.FavoriteVacancyStatusResponseDTO;
import com.devnotfound.talenthub.entity.CrawlerLog;
import com.devnotfound.talenthub.entity.Customer;
import com.devnotfound.talenthub.entity.CustomerCrawlerFavorite;
import com.devnotfound.talenthub.entity.CustomerCrawlerFavoriteId;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.CrawlerLogRepository;
import com.devnotfound.talenthub.repository.CustomerCrawlerFavoriteRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteVacancyService {

    private final AuthenticatedCustomerService authenticatedCustomerService;
    private final CrawlerLogRepository crawlerLogRepository;
    private final CustomerCrawlerFavoriteRepository customerCrawlerFavoriteRepository;

    @Transactional
    public void favorite(Integer crawlerId) {
        Customer customer = authenticatedCustomerService.getLoggedCustomer();

        if (customerCrawlerFavoriteRepository.existsByCustomer_IdAndCrawlerLog_Id(customer.getId(), crawlerId)) {
            return;
        }

        CrawlerLog crawlerLog = crawlerLogRepository.findById(crawlerId)
                .orElseThrow(() -> new ResourceNotFoundException("Vaga não encontrada com id: " + crawlerId));

        CustomerCrawlerFavorite favorite = new CustomerCrawlerFavorite();
        favorite.setId(new CustomerCrawlerFavoriteId(customer.getId(), crawlerId));
        favorite.setCustomer(customer);
        favorite.setCrawlerLog(crawlerLog);

        customerCrawlerFavoriteRepository.save(favorite);
    }

    @Transactional
    public void unfavorite(Integer crawlerId) {
        Customer customer = authenticatedCustomerService.getLoggedCustomer();
        customerCrawlerFavoriteRepository.deleteByCustomer_IdAndCrawlerLog_Id(customer.getId(), crawlerId);
    }

    @Transactional(readOnly = true)
    public List<FavoriteVacancyResponseDTO> listFavorites() {
        Customer customer = authenticatedCustomerService.getLoggedCustomer();
        return customerCrawlerFavoriteRepository.findFavoriteVacanciesByCustomerId(customer.getId());
    }

    @Transactional(readOnly = true)
    public FavoriteVacancyStatusResponseDTO getFavoriteStatus(Integer crawlerId) {
        Customer customer = authenticatedCustomerService.getLoggedCustomer();

        boolean favorite = customerCrawlerFavoriteRepository
                .existsByCustomer_IdAndCrawlerLog_Id(customer.getId(), crawlerId);

        return new FavoriteVacancyStatusResponseDTO(crawlerId, favorite);
    }
}