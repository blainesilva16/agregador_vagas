package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.entity.CrawlerLog;
import com.devnotfound.talenthub.repository.CrawlerLogRepository;
import com.devnotfound.talenthub.specification.CrawlerLogSpecification;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

//checar o pageable
@Service
public class CrawlerLogService {

    private final CrawlerLogRepository repository;

    public CrawlerLogService(CrawlerLogRepository repository) {
        this.repository = repository;
    }

    public Page<CrawlerLog> search(
            String plataform,
            Integer positionId,
            Integer techId,
            LocalDateTime startDate,
            LocalDateTime endDate,
            Pageable pageable
    ) { //findAll(Spec spec, Pageable pageable)
        return repository.findAll(CrawlerLogSpecification.filter(
                plataform,
                positionId,
                techId,
                startDate,
                endDate
        ), pageable);
    }

    public List<String> findDistinctPlataforms() {
        return repository.findDistinctPlataforms();
    }
}
