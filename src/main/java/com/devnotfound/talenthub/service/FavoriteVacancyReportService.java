package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.CrawlerLogFilterDTO;
import com.devnotfound.talenthub.dto.FavoriteVacancyReportDTO;
import com.devnotfound.talenthub.dto.FavoriteVacancyReportItemDTO;
import com.devnotfound.talenthub.dto.FavoriteVacancyResponseDTO;
import com.devnotfound.talenthub.entity.Customer;
import com.devnotfound.talenthub.pdf.PdfGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FavoriteVacancyReportService {

    private final AuthenticatedCustomerService authenticatedCustomerService;
    private final FavoriteVacancyService favoriteVacancyService;
    private final PdfGenerator<FavoriteVacancyReportDTO> favoriteVacancyPdfGenerator;

    @Transactional(readOnly = true)
    public byte[] generatePdf(CrawlerLogFilterDTO filterDTO) {
        Customer customer = authenticatedCustomerService.getLoggedCustomer();
        List<FavoriteVacancyResponseDTO> favorites = favoriteVacancyService.listFavorites(filterDTO);

        List<FavoriteVacancyReportItemDTO> items = favorites.stream()
                .map(f -> new FavoriteVacancyReportItemDTO(
                    f.title(),
                    f.companyName(),
                    f.ufAbrev(),
                    f.hiringType(),
                    f.workMode(),
                    f.salaryRange()
                ))
                .toList();

        FavoriteVacancyReportDTO reportDTO = new FavoriteVacancyReportDTO(
                customer.getName(),
                customer.getEmail(),
                LocalDateTime.now(),
                items.size(),
                items
        );

        return favoriteVacancyPdfGenerator.generate(reportDTO);
    }
}