package com.devnotfound.talenthub.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.devnotfound.talenthub.entity.ClienteFavorito;

public interface ClienteFavoritoRepository extends JpaRepository<ClienteFavorito, Long> {
	boolean existsByClienteIdAndVagaId(Long clienteId, String vagaId);

    void deleteByClienteIdAndVagaId(Long clienteId, String vagaId);

    List<ClienteFavorito> findByClienteId(Long clienteId);
}
