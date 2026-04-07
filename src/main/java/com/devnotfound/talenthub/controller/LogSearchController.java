package com.devnotfound.talenthub.controller;

import com.devnotfound.talenthub.repository.LogSearchRepository;
import com.devnotfound.talenthub.service.LogSearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("/top-searchs")
public class LogSearchController {

    private final LogSearchService logSearchService;

    @GetMapping
    public List<Map<String, Object>> getTopSearchs() {
        return logSearchService.getTopSearches();
    }
}
