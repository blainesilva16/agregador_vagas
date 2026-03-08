package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.CrawlerLogFilterDTO;
import com.devnotfound.talenthub.dto.CrawlerLogResponseDTO;
import com.devnotfound.talenthub.entity.CrawlerLog;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.mapper.CrawlerLogMapper;
import com.devnotfound.talenthub.repository.CrawlerLogRepository;
import com.devnotfound.talenthub.specification.CrawlerLogSpecification;
import com.devnotfound.talenthub.repository.PositionRepository;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CrawlerLogService {

    private final CrawlerLogRepository repository;
    private final PositionRepository positionRepository;

    public Page<CrawlerLogResponseDTO> findAllLogsLogged(CrawlerLogFilterDTO filterDTO, Pageable pageable) {

        Page<CrawlerLog> page = repository.findAll(
                CrawlerLogSpecification.filter(filterDTO), pageable);

        return page.map(CrawlerLogMapper::toResponseDTO);
    }

    public Page<CrawlerLogResponseDTO> findAllLogsUnlogged(CrawlerLogFilterDTO filterDTO, Pageable pageable) {

        Page<CrawlerLog> page = repository.findAll(
                CrawlerLogSpecification.filter(filterDTO), pageable);

        return page.map(CrawlerLogMapper::toResponseDTO);
    }

    /*public Page<CrawlerLogResponseDTO> findByPositionId(Integer id, Pageable pageable) {

        if (!positionRepository.existsById(id)) {
            throw new ResourceNotFoundException(
                    SystemConstants.JOB_NOT_FOUND_BY_POSITION + id
            );
        }

        Page<CrawlerLog> page =
                repository.findByPositionId(id, pageable);

        return page.map(CrawlerLogMapper::toResponseDTO);
      }*/

    public List<String> findDistinctPlataforms() {
        return repository.findDistinctPlataforms();
    }
}
