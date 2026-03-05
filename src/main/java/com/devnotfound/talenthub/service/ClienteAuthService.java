package com.devnotfound.talenthub.service;

import com.devnotfound.talenthub.constants.SystemConstants;
import com.devnotfound.talenthub.entity.Cliente;
import com.devnotfound.talenthub.exception.ResourceNotFoundException;
import com.devnotfound.talenthub.repository.ClienteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.BadCredentialsException;

@Service
@RequiredArgsConstructor
public class ClienteAuthService {
	
	private final ClienteRepository clienteRepository;
	private final PasswordEncoder passwordEncoder;
	
    public Cliente authenticate(String email, String password) {

        Cliente cliente = clienteRepository.findByEmail(email)
        		.orElseThrow(() -> new ResourceNotFoundException(SystemConstants.CLIENT_NOT_FOUND_EMAIL + email));

        if (!passwordEncoder.matches(password, cliente.getPassword())) {
            throw new BadCredentialsException(SystemConstants.CLIENT_NOT_FOUND_CREDENTALS); //401
        }
        return cliente;
    }
}