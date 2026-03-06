package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.dto.ClienteFavoritoResponseDTO;
import com.devnotfound.talenthub.entity.Cliente;
import com.devnotfound.talenthub.entity.ClienteFavorito;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.ClienteFavoritoRepository;
import com.devnotfound.talenthub.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteFavoritoService {

    private final ClienteRepository clienteRepository;
    private final ClienteFavoritoRepository favoritoRepository;

    public void favoritar(String vagaId) {
        Cliente cliente = getClienteLogado();

        if (favoritoRepository.existsByClienteIdAndVagaId(cliente.getId(), vagaId)) {
            return; 
        }

        ClienteFavorito clienteFavorito = new ClienteFavorito();
        clienteFavorito.setCliente(cliente);
        clienteFavorito.setVagaId(vagaId);

        favoritoRepository.save(clienteFavorito);
    }

    public void desfavoritar(String vagaId) {
        Cliente cliente = getClienteLogado();
        favoritoRepository.deleteByClienteIdAndVagaId(cliente.getId(), vagaId);
    }

    public List<ClienteFavoritoResponseDTO> listar() {
        Cliente cliente = getClienteLogado();

        return favoritoRepository.findByClienteId(cliente.getId())
                .stream()
                .map(f -> new ClienteFavoritoResponseDTO(f.getVagaId(), f.getCreatedAt()))
                .toList();
    }

    private Cliente getClienteLogado() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();

        return clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente logado " + email + " não encontrado."));
    }
}