package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.VagaFavoritoResponseDTO;
import com.devnotfound.talenthub.entity.Cliente;
import com.devnotfound.talenthub.entity.VagaFavorito;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.VagaFavoritoRepository;
import com.devnotfound.talenthub.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class VagaFavoritoService {

    private final ClienteRepository clienteRepository;
    private final VagaFavoritoRepository favoritoRepository;

    public void favoritar(String vagaId) {
        Cliente cliente = getClienteLogado();

        if (favoritoRepository.existsByClienteIdAndVagaId(cliente.getId(), vagaId)) {
            return; 
        }

        VagaFavorito vagaFavorito = new VagaFavorito();
        vagaFavorito.setCliente(cliente);
        vagaFavorito.setVagaId(vagaId);

        favoritoRepository.save(vagaFavorito);
    }

    public void desfavoritar(String vagaId) {
        Cliente cliente = getClienteLogado();
        favoritoRepository.deleteByClienteIdAndVagaId(cliente.getId(), vagaId);
    }

    public List<VagaFavoritoResponseDTO> listar() {
        Cliente cliente = getClienteLogado();

        return favoritoRepository.findByClienteId(cliente.getId())
                .stream()
                .map(f -> new VagaFavoritoResponseDTO(f.getVagaId(), f.getCreatedAt()))
                .toList();
    }

    private Cliente getClienteLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente logado " + email + " não encontrado."));
    }
}