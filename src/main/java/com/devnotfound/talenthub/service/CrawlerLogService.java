package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.CrawlerLogFilterDTO;
import com.devnotfound.talenthub.dto.CrawlerLogResponseDTO;
import com.devnotfound.talenthub.entity.CrawlerLog;
import com.devnotfound.talenthub.mapper.CrawlerLogMapper;
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

    public Page<CrawlerLogResponseDTO> search(CrawlerLogFilterDTO filterDTO, Pageable pageable) {
        //findAll(Spec spec, Pageable pageable)
        Page<CrawlerLog> page =
                repository.findAll(
                        CrawlerLogSpecification.filter(
                                filterDTO), pageable);

        return page.map(CrawlerLogMapper::toResponseDTO);
    }

    public Page<CrawlerLogResponseDTO> findByPosition_Id(Integer id, Pageable pageable) {

        Page<CrawlerLog> page =
                repository.findByPosition_Id(id, pageable);

        return page.map(CrawlerLogMapper::toResponseDTO);
    }

    public List<String> findDistinctPlataforms() {
        return repository.findDistinctPlataforms();
    }
}
