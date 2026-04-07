package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.LogSearch;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LogSearchRepository extends JpaRepository<LogSearch, Integer> {

    @Query("SELECT l.itemSearch, COUNT(l) as total FROM LogSearch l " +
    "GROUP BY l.itemSearch ORDER BY total DESC")
    List<Object[]> findTopSearches(Pageable pageable);
}
