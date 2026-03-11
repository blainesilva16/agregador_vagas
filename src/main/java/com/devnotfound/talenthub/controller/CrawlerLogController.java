package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.dto.CrawlerLogFilterDTO;
import com.devnotfound.talenthub.dto.CrawlerLogResponseDTO;
import com.devnotfound.talenthub.entity.CrawlerLog;
import com.devnotfound.talenthub.repository.CrawlerLogRepository;
import com.devnotfound.talenthub.service.CrawlerLogService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
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
    public Page<CrawlerLogResponseDTO> searchUnlloged(@ParameterObject CrawlerLogFilterDTO filterDTO,
                                                      @ParameterObject Pageable pageable) {
        return service.findAllLogsUnlogged(filterDTO, pageable);
    }

    @GetMapping("/private")
    public Page<CrawlerLogResponseDTO> searchLogged(@ParameterObject CrawlerLogFilterDTO filterDTO,
                                                    @ParameterObject Pageable pageable) {
        return service.findAllLogsLogged(filterDTO, pageable);
    }

    @GetMapping("/positions/{id}")
    public Page<CrawlerLogResponseDTO> findByPositionId(
            @PathVariable Integer id,
            Pageable pageable
    ) {
        return service.findByPositionId(id, pageable);
    }

    @GetMapping("/plataforms")
    public List<String> getPlataforms() {
        return service.findDistinctPlataforms();
    }

    @GetMapping("/states")
    public List<String> getDistinctStates() {
        return service.findDistinctUf();
    }

    @GetMapping("/cities")
    public List<String> getDistinctCitiesByUf(@RequestParam String ufAbrev) {
        return service.findCitiesByState(ufAbrev);
    }
}
