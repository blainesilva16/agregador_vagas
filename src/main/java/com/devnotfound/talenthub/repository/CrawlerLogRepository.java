package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.CrawlerLog;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CrawlerLogRepository extends JpaRepository<CrawlerLog, Integer> {
}