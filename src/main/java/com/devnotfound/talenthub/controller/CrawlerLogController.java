package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.entity.CrawlerLog;
import com.devnotfound.talenthub.repository.CrawlerLogRepository;
import com.devnotfound.talenthub.service.CrawlerLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/jobs")
@RequiredArgsConstructor //injeta dependencias via construtor
public class CrawlerLogController {

    private final CrawlerLogService service;

    @GetMapping
    public Page<CrawlerLog> search(
            @RequestParam(required = false) String plataform,
            @RequestParam(required = false) Integer positionId,
            @RequestParam(required = false) Integer techId,
            @RequestParam(required = false) LocalDateTime startDate,
            @RequestParam(required = false) LocalDateTime endDate,
            Pageable pageable
    ) {
        return service.search(
                plataform,
                positionId,
                techId,
                startDate,
                endDate,
                pageable);
    }

    @GetMapping("/plataforms")
    public List<String> getPlataforms() {
        return service.findDistinctPlataforms();
    }
}
