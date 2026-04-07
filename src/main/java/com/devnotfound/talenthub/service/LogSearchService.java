package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.entity.LogSearch;
import com.devnotfound.talenthub.repository.LogSearchRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class LogSearchService {
    
    private final LogSearchRepository logSearchRepository;
    
    @Async
    public void setLogSearch(String itemSearch) {
        LogSearch log = new LogSearch();
        log.setItemSearch(itemSearch.toLowerCase().trim());
        log.setDateSearch(LocalDateTime.now());
        logSearchRepository.save(log);
    }

    public List<Map<String, Object>> getTopSearches() {
        // O PageRequest já implementa Pageable, não precisa de cast
        Pageable pageable = PageRequest.of(0, 10);

        return logSearchRepository.findTopSearches(pageable)
                .stream()
                .map(obj -> Map.of(
                        "item_search", obj[0],
                        "quantidade", obj[1]
                ))
                .toList();
    }
}
