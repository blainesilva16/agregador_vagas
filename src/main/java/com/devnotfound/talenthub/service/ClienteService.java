package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.ClienteRequestDTO;
import com.devnotfound.talenthub.dto.ClienteResponseDTO;
import com.devnotfound.talenthub.entity.Cliente;
import com.devnotfound.talenthub.exception.DuplicateEmailException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.mapper.ClienteMapper;
import com.devnotfound.talenthub.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClienteService {

    private final ClienteRepository clienteRepository;
    private final PasswordEncoder passwordEncoder;

    public ClienteResponseDTO findByEmail(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ResourceNotFoundException(SystemConstants.CLIENT_NOT_FOUND_EMAIL + email);
        }
        return ClienteMapper.toResponseDTO(cliente);
    }

    public ClienteResponseDTO insert(ClienteRequestDTO dto) {
        Cliente existing = clienteRepository.findByEmail(dto.email());
        if (existing != null) {
            throw new DuplicateEmailException(SystemConstants.EMAIL_ALREADY_EXISTS + dto.email());
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setPassword(passwordEncoder.encode(dto.password()));

        Cliente saved = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDTO(saved);
    }

    public String resetPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email);
        if (cliente == null) {
            throw new ResourceNotFoundException(SystemConstants.CLIENT_NOT_FOUND_EMAIL + email);
        }

        String newPassword = UUID.randomUUID()
        		.toString()
        		.substring(0, 8);
        cliente.setPassword(passwordEncoder.encode(newPassword));
        clienteRepository.save(cliente);

        return newPassword;
    }
}