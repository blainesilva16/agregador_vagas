package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.CrawlerLogFilterDTO;
import com.devnotfound.talenthub.dto.CrawlerLogResponseDTO;
import com.devnotfound.talenthub.entity.CrawlerLog;
import com.devnotfound.talenthub.repository.CrawlerLogRepository;
import com.devnotfound.talenthub.service.CrawlerLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor //injeta dependencias via construtor
public class CrawlerLogController {

    private final CrawlerLogService service;

    @GetMapping("/public")
    public Page<CrawlerLogResponseDTO> publicSearch(Pageable pageable) {
        return service.findAllLogsUnlogged(pageable);
    }

    @GetMapping("/private")
    public Page<CrawlerLogResponseDTO> privateSearch(Pageable pageable) {
        return service.findAllLogsLogged(pageable);
    }

    //metodo de listagem/busca com filtro e paginação.
    @GetMapping
    public Page<CrawlerLogResponseDTO> search(
            CrawlerLogFilterDTO filterDTO,
            Pageable pageable
    ) {
        return service.search(
                filterDTO,
                pageable);
    }

    //endpoint REST q retorna uma lista de valores únicos do campo plataform
    @GetMapping("/plataforms")
    public List<String> getPlataforms() {
        return service.findDistinctPlataforms();
    }

    /*@GetMapping("/positions/{id}")
    public Page<CrawlerLogResponseDTO> findByPositionId(
            @PathVariable Integer id,
            Pageable pageable
    ) {
        return service.findByPositionId(id, pageable);
    }*/
}
