package com.devnotfound.talenthub.mapper;

import com.devnotfound.talenthub.dto.ClienteRequestDTO;
import com.devnotfound.talenthub.dto.ClienteResponseDTO;
import com.devnotfound.talenthub.entity.Cliente;

public final class ClienteMapper {

    private ClienteMapper() {}

    public static Cliente toEntity(ClienteRequestDTO dto) {
        Cliente cliente = new Cliente();
        cliente.setEmail(dto.email());
        return cliente;
    }

    public static ClienteResponseDTO toResponseDTO(Cliente cliente) {
        return new ClienteResponseDTO(
        		cliente.getId(), 
        		cliente.getEmail()
        );
    }
}
