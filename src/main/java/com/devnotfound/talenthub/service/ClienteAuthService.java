package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.entity.Cliente;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClienteAuthService {
	
	private final ClienteRepository clienteRepository;
	private final PasswordEncoder passwordEncoder;
	
    public void authenticate(String email, String password) {

        Cliente cliente = clienteRepository.findByEmail(email);

        if (cliente == null) {
            throw new ResourceNotFoundException("Cliente não encontrado");
        }

        if (!passwordEncoder.matches(password, cliente.getPassword())) {
            throw new RuntimeException("Senha inválida");
        }
    }
}