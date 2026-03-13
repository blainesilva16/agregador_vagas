package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.FavoriteVacancyResponseDTO;
import com.devnotfound.talenthub.dto.FavoriteVacancyStatusResponseDTO;
import com.devnotfound.talenthub.service.FavoriteVacancyService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favorite")
@RequiredArgsConstructor
public class FavoriteVacancyController {

    private final FavoriteVacancyService favoriteVacancyService;

    @PostMapping("/{crawlerId}")
    public ResponseEntity<Void> favorite(@PathVariable Integer crawlerId) {
        favoriteVacancyService.favorite(crawlerId);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{crawlerId}")
    public ResponseEntity<Void> unfavorite(@PathVariable Integer crawlerId) {
        favoriteVacancyService.unfavorite(crawlerId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping
    public ResponseEntity<List<FavoriteVacancyResponseDTO>> listFavorites() {
        return ResponseEntity.ok(favoriteVacancyService.listFavorites());
    }

    @GetMapping("/{crawlerId}/status")
    public ResponseEntity<FavoriteVacancyStatusResponseDTO> getFavoriteStatus(@PathVariable Integer crawlerId) {
        return ResponseEntity.ok(favoriteVacancyService.getFavoriteStatus(crawlerId));
    }
}