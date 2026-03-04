package com.devnotfound.talenthub.repository;

import com.devnotfound.talenthub.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {
    // Você só precisa de busca por nome para os cargos
    Position findByName(String name);
    List<Position> findByNameContainingIgnoreCase(String name);
}