package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.CrawlerLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

//JpaSpecificationExecutor habilita findAll(Specification<T> spec, Pageable pageable)
@Repository
public interface CrawlerLogRepository extends JpaRepository<CrawlerLog, Integer>
        , JpaSpecificationExecutor<CrawlerLog> {

    Page<CrawlerLog> findByPosition_Id(Integer id, Pageable pageable);

    @Query("SELECT DISTINCT c.plataform FROM CrawlerLog c")
    List<String> findDistinctPlataforms();
}
