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
import com.devnotfound.talenthub.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteVacancyService {

    private final CustomerRepository customerRepository;
    private final CrawlerLogRepository crawlerLogRepository;
    private final CustomerCrawlerFavoriteRepository customerCrawlerFavoriteRepository;

    @Transactional
    public void favorite(Integer crawlerId) {
        Customer customer = getLoggedCustomer();

        if (customerCrawlerFavoriteRepository.existsByCustomerIdAndCrawlerLogId(customer.getId(), crawlerId)) {
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
        Customer customer = getLoggedCustomer();
        customerCrawlerFavoriteRepository.deleteByCustomerIdAndCrawlerLogId(customer.getId(), crawlerId);
    }

    @Transactional(readOnly = true)
    public List<FavoriteVacancyResponseDTO> listFavorites() {
        Customer customer = getLoggedCustomer();
        return customerCrawlerFavoriteRepository.findFavoriteVacanciesByCustomerId(customer.getId());
    }

    @Transactional(readOnly = true)
    public FavoriteVacancyStatusResponseDTO getFavoriteStatus(Integer crawlerId) {
        Customer customer = getLoggedCustomer();

        boolean favorite = customerCrawlerFavoriteRepository
                .existsByCustomerIdAndCrawlerLogId(customer.getId(), crawlerId);

        return new FavoriteVacancyStatusResponseDTO(crawlerId, favorite);
    }

    private Customer getLoggedCustomer() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()
                || "anonymousUser".equals(authentication.getName())) {
            throw new BadCredentialsException("Customer não autenticado.");
        }

        String email = authentication.getName();

        return customerRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Customer logado não encontrado: " + email));
    }
}