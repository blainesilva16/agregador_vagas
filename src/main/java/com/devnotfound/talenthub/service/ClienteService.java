package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.dto.ClienteRequestDTO;
import com.devnotfound.talenthub.dto.ClienteResponseDTO;
import com.devnotfound.talenthub.dto.ClienteUpdateDTO;
import com.devnotfound.talenthub.dto.UserRequestDTO;
import com.devnotfound.talenthub.dto.UserResponseDTO;
import com.devnotfound.talenthub.entity.Cliente;
import com.devnotfound.talenthub.entity.User;
import com.devnotfound.talenthub.exception.DuplicateEmailException;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.mapper.ClienteMapper;
import com.devnotfound.talenthub.mapper.UserMapper;
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

    public ClienteResponseDTO insert(ClienteRequestDTO dto) {
        if (clienteRepository.existsByEmail(dto.email())) {
            throw new DuplicateEmailException(SystemConstants.EMAIL_ALREADY_EXISTS + dto.email());
        }

        Cliente cliente = ClienteMapper.toEntity(dto);
        cliente.setPassword(passwordEncoder.encode(dto.password()));

        Cliente saved = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDTO(saved);
    }

    public String resetPassword(String email) {
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CLIENT_NOT_FOUND_EMAIL + email));

        String newPassword = SystemConstants.DEFAULT_SITE_PASSWORD; // "Cli!23"
        cliente.setPassword(passwordEncoder.encode(newPassword));
        clienteRepository.save(cliente);

        return newPassword;
    }
    
    public ClienteResponseDTO update(Long id, ClienteUpdateDTO clienteUpdateDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente não encontrado com id: " + id));

        if (clienteUpdateDTO.email() != null && !clienteUpdateDTO.email().equals(cliente.getEmail())) {
            if (clienteRepository.existsByEmail(clienteUpdateDTO.email())) {
                throw new DuplicateEmailException(SystemConstants.EMAIL_ALREADY_EXISTS + clienteUpdateDTO.email());
            }
            cliente.setEmail(clienteUpdateDTO.email());
        }

        if (clienteUpdateDTO.name() != null) cliente.setName(clienteUpdateDTO.name());
        if (clienteUpdateDTO.birthdate() != null) cliente.setBirthdate(clienteUpdateDTO.birthdate());
        if (clienteUpdateDTO.photo() != null) cliente.setPhoto(clienteUpdateDTO.photo());

        Cliente saved = clienteRepository.save(cliente);
        return ClienteMapper.toResponseDTO(saved);
    }

    public void delete(Long id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente não encontrado com id: " + id);
        }
        clienteRepository.deleteById(id);
    }
    
}