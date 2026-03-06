package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.ClienteRequestDTO;
import com.devnotfound.talenthub.dto.ClienteResponseDTO;
import com.devnotfound.talenthub.dto.ClienteUpdateDTO;
import com.devnotfound.talenthub.entity.Cliente;
import com.devnotfound.talenthub.exception.DuplicateEmailException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.mapper.ClienteMapper;
import com.devnotfound.talenthub.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteResponseDTO findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CLIENT_NOT_FOUND_EMAIL + email));
        return ClienteMapper.toResponseDTO(cliente);
    }

    public ClienteResponseDTO findById(Long id) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));
        return ClienteMapper.toResponseDTO(cliente);
    }

    public List<ClienteResponseDTO> findAll() {
        return clienteRepository.findAll().stream()
                .map(ClienteMapper::toResponseDTO)
                .toList();
    }

    public List<ClienteResponseDTO> findByName(String name) {
        List<ClienteResponseDTO> clientes = clienteRepository.findByNameContainingIgnoreCase(name).stream()
                .map(ClienteMapper::toResponseDTO)
                .toList();

        if (clientes.isEmpty()) {
            throw new ResourceNotFoundException("Cliente não encontrado com nome: " + name);
        }

        return clientes;
    }

    public ClienteResponseDTO insert(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException(SystemConstants.EMAIL_ALREADY_EXISTS + dto.email());
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setPassword(passwordEncoder.encode(dto.password()));

        Cliente saved = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDTO(saved);
    }

    public ClienteResponseDTO update(Long id, ClienteUpdateDTO dto) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));

        if (dto.email() != null && !dto.email().equals(cliente.getEmail())) {
            if (clienteRepository.existsByEmail(dto.email())) {
                throw new DuplicateEmailException(SystemConstants.EMAIL_ALREADY_EXISTS + dto.email());
            }
            cliente.setEmail(dto.email());
        }

        if (dto.name() != null) {
            cliente.setName(dto.name());
        }
        if (dto.birthdate() != null) {
            cliente.setBirthdate(dto.birthdate());
        }
        if (dto.photo() != null) {
            cliente.setPhoto(dto.photo());
        }

        Cliente updated = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDTO(updated);
    }

    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
        clienteRepository.deleteById(id);
    }

    public String resetPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CLIENT_NOT_FOUND_EMAIL + email));

        String newPassword = SystemConstants.DEFAULT_SITE_PASSWORD; // "Cli!23"
        cliente.setPassword(passwordEncoder.encode(newPassword));
        clienteRepository.save(cliente);

        return newPassword;
    }
}