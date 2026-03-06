package com.devnotfound.talenthub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devnotfound.talenthub.entity.VagaFavorito;

public interface VagaFavoritoRepository extends JpaRepository<VagaFavorito, Long> {
	boolean existsByClienteIdAndVagaId(Long clienteId, String vagaId);

    void deleteByClienteIdAndVagaId(Long clienteId, String vagaId);

    List<VagaFavorito> findByClienteId(Long clienteId);
}
