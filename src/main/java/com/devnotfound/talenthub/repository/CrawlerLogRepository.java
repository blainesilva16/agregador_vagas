package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.CrawlerLog;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

<<<<<<< HEAD
public interface CrawlerLogRepository extends JpaRepository<CrawlerLog, Integer> {
}
=======
import java.util.List;

//JpaSpecificationExecutor habilita findAll(Specification<T> spec, Pageable pageable)
@Repository
public interface CrawlerLogRepository extends JpaRepository<CrawlerLog, Integer>
        , JpaSpecificationExecutor<CrawlerLog> {

    boolean existsByPositionId(Integer id);

    Page<CrawlerLog> findByPositionId(Integer id, Pageable pageable);

    //Page<CrawlerLog> findByTechId(Integer id, Pageable pageable);

    @Query("SELECT DISTINCT c.plataform FROM CrawlerLog c")
    List<String> findDistinctPlataforms();

    boolean existsByTechId(Integer id);

    @Query("SELECT DISTINCT c.ufName FROM CrawlerLog c")
    List<String> findDistinctUf();

    @Query("SELECT DISTINCT c.cityName FROM CrawlerLog c WHERE c.ufAbrev = :ufAbrev")
    List<String> findCitiesByUf(@Param("ufAbrev") String ufAbrev);

}
>>>>>>> main
