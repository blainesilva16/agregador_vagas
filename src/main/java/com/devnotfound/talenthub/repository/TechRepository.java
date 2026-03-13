package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.Tech;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TechRepository extends JpaRepository<Tech, Integer> {

    boolean existsByNameIgnoreCase(String name);

    List<Tech> findByNameContainingIgnoreCase(String name);

}
